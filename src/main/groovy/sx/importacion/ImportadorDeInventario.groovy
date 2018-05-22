package sx.importacion

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import sx.DataSourceReplica
import sx.EntityConfiguration
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.simple.SimpleJdbcInsert



@Component
class ImportadorDeInventario{

  @Autowired
  @Qualifier('dataSourceLocatorService')
  def dataSourceLocatorService
  @Autowired
  @Qualifier('dataSource')
  def dataSource
  @Autowired
  @Qualifier('replicaOperacionService')
  def replicaOperacionService

  def importar(){
      importar(new Date())
  }

  def importar(fecha){
  //  println ("Importando OperacionesAlmacen del : ${fecha.format('dd/MM/yyyy')}" )

    def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

      def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

      servers.each(){server ->

//        println "***  Importando de Por ReplicaService: ${server.server} ******* ${server.url}****  "
        importarServerFecha(server,fecha)
      }
  }


  def importarSucursalFecha(nombreSuc,fecha){

    def server=DataSourceReplica.findByServer(nombreSuc)
//    println  "*************************************************************"
//    println "nombre: ${nombreSuc} fecha: ${fecha.format('dd/MM/yyyy')} URL: ${server.url} "

    importarServerFecha(server,fecha)

  }

  def importarServerFecha(server,fecha){

  //  def fecha=fecha.format('yyyy/MM/dd')

  //  println "Importando Por Server Fecha   "+fecha+ "   "+server.server
    def dataSourceSuc=dataSourceLocatorService.dataSourceLocatorServer(server)
    def sqlSuc=new Sql(dataSourceSuc)
    def sqlCen=new Sql(dataSource)

    def configInv= EntityConfiguration.findByName("Inventario")

    def queryInv="select * from inventario where tipo not in ('RMD','TPS','FAC','TPE') AND DATE(FECHA)=? "

    def inventarios=sqlSuc.rows(queryInv,[fecha])
    inventarios.each{ inventarioSuc ->
          def queryInvId="select *  from inventario where id=?"

          def invCen=sqlCen.firstRow(queryInvId,[inventarioSuc.id])

          if(!invCen){
      //      println "El registro de inventario no ha sido importado se debe importar"
          //  SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("inventario")
          //  def res=insert.execute(inventarioSuc)

          }else{
    //        println "EL registro de inventario ya fue importado Solo actualizar"
            //sqlCen.executeUpdate(inventarioSuc, configInv.updateSql)
          }
            def table=""
          switch(inventarioSuc.tipo) {
            case 'TRS':
            case 'REC':
                  table='transformacion'
            break
            case 'COM':
                  table='recepcion_de_compra'

            break
            case 'DEC':
                  table='devolucion_de_compra'

            break
            case 'MER':
            case 'CIS':
            case 'VIR':
            case 'CIM':
            case 'OIM':
            case 'RMC':
                    table='movimiento_de_almacen'
            break
            default:

            break
          }
  //          println "******************"+table
          def queryPartida="select * from ${table}_det where inventario_id=?"

          def partidaSuc=sqlSuc.firstRow(queryPartida,[inventarioSuc.id])

          if(partidaSuc){
    //        println partidaSuc.partidas_idx

            if(partidaSuc.partidas_idx == 0){
              /*Revisio  del maestro*/

              def queryMovId="Select distinct(m.id) as id from  ${table} m join ${table}_det d on (m.id=d.${table}_id) join inventario i on (i.id=d.inventario_id) where i.id=?"
              if(table=='recepcion_de_compra'){
                 queryMovId="Select distinct(m.id) as id from  ${table} m join ${table}_det d on (m.id=d.recepcion_id) join inventario i on (i.id=d.inventario_id) where i.id=?"
              }
              def movId=sqlSuc.firstRow(queryMovId,[inventarioSuc.id])

              def queryMov="select * from ${table} where id=?"

              def moviSuc=sqlSuc.firstRow(queryMov,[movId.id])
              if(moviSuc){
    //            println "------------------------Partida"+moviSuc.id
                def moviCen=sqlCen.firstRow(queryMov,[movId.id])

                if(!moviCen){
    //              println "------------------------Importar"+moviSuc.id
                //  SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("${table}")
                //  def res=insert.execute(moviSuc)
                }
              }
            }
            def partidaCen=sqlCen.firstRow(queryPartida,[inventarioSuc.id])
            if(!partidaCen){
  //            println "Importando movimiento partida"+partidaSuc.id +" ---- "+ partidaSuc.inventario_id

            //SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("${table}_det")
            //    def res=insert.execute(partidaSuc)
            }else{
  //            println "Actualizando movimiento partida"+partidaSuc.id +" ---- "+ partidaSuc.inventario_id

            }


          }


    }
  }

}

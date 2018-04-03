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
class ImportadorDeRecepcionDeCompras {

    @Autowired
    @Qualifier('dataSourceLocatorService')
    def dataSourceLocatorService

    @Autowired
    @Qualifier('dataSource')
    def dataSource

  def importar(){
    def fecha= new Date()

    importar(fecha)

    }

    def importar(fecha){
      println ("Importando Compras del : ${fecha.format('dd/MM/yyyy')}" )

      def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

        def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

        servers.each(){server ->
          println "***  Importando de Por ReplicaService: ${server.server} ******* ${server.url}****  "
          importarServerFecha(server,fecha)
        }
    }



    def importarSucursalFecha(nombreSuc,fecha){

      def server=DataSourceReplica.findByServer(nombreSuc)

      println "nombre: ${nombreSuc} fecha: ${fecha.format('dd/MM/yyyy')} URL: ${server} "

      importarServerFecha(server,fecha)

    }

    def importarServerFecha(server,fecha){

      println "Importando Por Server Fecha"+fecha.format('yyyy/MM/dd')
      def dataSourceSuc=dataSourceLocatorService.dataSourceLocatorServer(server)
      def sqlSuc=new Sql(dataSourceSuc)
      def sqlCen=new Sql(dataSource)

      def config= EntityConfiguration.findByName("RecepcionDeCompra")
      def configDet= EntityConfiguration.findByName("RecepcionDeCompraDet")
      def configInv= EntityConfiguration.findByName("Inventario")

      def querySuc="Select * from recepcion_de_compra where fecha=?"

      def entradas=sqlSuc.rows(querySuc,[fecha.format('yyyy/MM/dd')])

      entradas.each{entradaSuc ->

        def queryCen="Select * from recepcion_de_compra where id=? "

        def entradaCen=sqlCen.firstRow(queryCen,[entradaSuc.id])

        if(entradaCen){
          println "EL registro de entrada ya fue importado Solo actualizar"
          //  sqlCen.executeUpdate(entradaSuc, config.updateSql)
        }else{
          println "El registro de entrada no ha sido importado se debe importar"

        //  SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("recepcion_de_compra_det")
        //  def res=insert.execute(entradaSuc)
        }

        def queryPartidas="Select * from recepcion_de_compra_det where recepcion_de_compra_id=?"
        def partidas=sqlSuc.rows(queryPartidas,[entradaSuc.id])

            partidas.each{partidaSuc ->

              def queryPartidaCen="Select * from recepcion_de_compra_det where id=?"
              def partidaCen=sqlCen.firstRow(queryPartidaCen,[partidaSuc.id])

              if(partidaSuc.inventario_id){

                  def queryInv="Select * from inventario where id=?"

                  def inventarioSuc=sqlSuc.firstRow(queryInv,[partidaSuc.inventario_id])

                  def inventarioCen=sqlCen.firstRow(queryInv,[partidaSuc.inventario_id])

                  if(inventarioCen){
                      //sqlCen.executeUpdate(inventarioSuc, configInv.updateSql)
                  }else{
                    //SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("inventario")
                    //def res=insert.execute(inventarioSuc)
                  }


              }

              if(partidaCen){
                println "EL registro de partida ya fue importado Solo actualizar"
                //sqlCen.executeUpdate(partidaSuc, configDet.updateSql)
              }else{
                println "El registro de partida no ha sido importado se debe importar"
                //SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("compra_det")
                //def res=insert.execute(partidaSuc)
              }


      }

      }
    }

    def importadorInventarioCompras(fecha){

      println "Importando Por Server Fecha"+fecha.format('yyyy/MM/dd')
      def dataSourceSuc=dataSourceLocatorService.dataSourceLocatorServer(server)
      def sqlSuc=new Sql(dataSourceSuc)
      def sqlCen=new Sql(dataSource)

      def configInv= EntityConfiguration.findByName("Inventario")
      def configDet= EntityConfiguration.findByName("RecepcionDeCompraDet")
    }

}

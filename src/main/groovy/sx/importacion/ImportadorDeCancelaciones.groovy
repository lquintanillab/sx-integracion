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
class ImportadorDeCancelaciones{

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
  //  println ("Importando Cancelaciones del : ${fecha.format('dd/MM/yyyy')}" )

    def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

      def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

      servers.each(){server ->

      //  println "***  Importando de Por ReplicaService: ${server.server} ******* ${server.url}****  "
        importarServerFecha(server,fecha)
      }
  }


  def importarSucursalFecha(nombreSuc,fecha){

    def server=DataSourceReplica.findByServer(nombreSuc)
  //  println  "*************************************************************"
  //  println "nombre: ${nombreSuc} fecha: ${fecha.format('dd/MM/yyyy')} URL: ${server.url} "

    importarServerFecha(server,fecha)

  }

  def importarServerFecha(server,fechaImpo){

    def fecha=fechaImpo.format('yyyy/MM/dd')

  //  println "Importando Por Server Fecha   "+fecha+ "   "+server.server
    def dataSourceSuc=dataSourceLocatorService.dataSourceLocatorServer(server)
    def sqlSuc=new Sql(dataSourceSuc)
    def sqlCen=new Sql(dataSource)
    def configCxc= EntityConfiguration.findByName("CuentaPorCobrar")

    def queryCancelacionSuc="select * from cfdi_cancelado where date(date_created)=?"
    //def queryCancelacionSuc="select * from cfdi_cancelado where date(date_created)='2018/04/10'"

    def cancelacionesSuc=sqlSuc.rows(queryCancelacionSuc,[fechaImpo])
    //def cancelacionesSuc=sqlSuc.rows(queryCancelacionSuc)

    cancelacionesSuc.each{cancelacion ->
      //  println "-- "+cancelacion.id
          def queryCancelacionCen="select * from cfdi_cancelado where id=? "

          def cancelacionCen=sqlCen.firstRow(queryCancelacionCen,[cancelacion.id])

          if(!cancelacionCen){

        //    println "Importando Cancelacion de Cfdi"+cancelacion.uuid
            SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("cfdi_cancelado")
            def res=insert.execute(cancelacion)

            }

            def queryCxcCen="Select * from cuenta_por_cobrar where uuid=?"

            def queryCxcSuc="select * from cuenta_por_cobrar where id=?"

            def cxcCen=sqlCen.firstRow(queryCxcCen,[cancelacion.uuid])

            if(cxcCen){
          //    println "Actualizando cuenta_por_cobrar de "+cancelacion.uuid
              def cxcSuc=sqlSuc.firstRow(queryCxcSuc,[cxcCen.id])
              sqlCen.executeUpdate(cxcSuc, configCxc.updateSql)
          }


    }


  }

}

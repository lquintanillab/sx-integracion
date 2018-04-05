package sx.importacion

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import sx.DataSourceReplica
import sx.EntityConfiguration
import sx.Sucursal
import sx.AuditLog
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.simple.SimpleJdbcInsert



@Component
class ImportadorDeTraslados{


  @Autowired
   @Qualifier('dataSourceLocatorService')
  def dataSourceLocatorService
  @Autowired
  @Qualifier('dataSource')
  def dataSource
  @Autowired
  @Qualifier('replicaService')
  def replicaService

  def importar(){

    println ("Importando Traslados" )

    def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

      def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

      servers.each{server ->
            println ("Importando Traslados"+server.server+"*************"+server.url )
        importarServer(server)
      }

  }

  def importarSucursal(nombreSuc){

    def server=DataSourceReplica.findByServer(nombreSuc)
      importarServer(server)
  }

    def importarServer(server){

      println ("Importando Traslados"+server.server+"*************"+server.url )

      def dataSourceSuc=dataSourceLocatorService.dataSourceLocatorServer(server)

      def sqlSuc=new Sql(dataSourceSuc)
      def sqlCen=new Sql(dataSource)

      def config= EntityConfiguration.findByName("Traslado")
      def configDet= EntityConfiguration.findByName("TrasladoDet")
      def confgCfdi= EntityConfiguration.findByName("Cfdi")
      def configInvent= EntityConfiguration.findByName("Inventario")

      def queryAuditLog="Select * from audit_log where date_replicated is null and name='Traslado' and date(date_created>='2018/04/05')"

      def audits=sqlSuc.rows(queryAuditLog)

      def queryId="select * from traslado  where id=?"

      audits.each{ audit ->

           println "***  Importando Traslados: ${server.server} ******* ${server.url}****  "
          println audit

          def trdSuc=sqlSuc.firstRow(queryId,[audit.persisted_object_id])

          if(trdSuc || audit.event_name=='DELETE'){

          // try{
              switch(audit.event_name) {
                case 'INSERT':

                break
                case 'UPDATE':

                break

                case 'DELETE':

                break

                default:

                break
              }

    /*       }
         catch (DuplicateKeyException dk) {
                  println dk.getMessage()
              //    println "Registro duplicado ${audit.id} -- ${audit.persisted_object_id}"
                  sqlSuc.execute("UPDATE AUDIT_LOG SET DATE_REPLICATED=NOW(),MESSAGE=? WHERE ID=? ", ["Registro duplicado",audit.id])

              }catch (Exception e){
                  e.printStackTrace()
                String err="Error importando a central: "

                  sqlSuc.execute("UPDATE AUDIT_LOG SET MESSAGE=?,DATE_REPLICATED=null WHERE ID=? ", [err,audit.id])
              }*/

          }
          else{
          //  sqlSuc.execute("UPDATE AUDIT_LOG SET MESSAGE='NO EXISTE',DATE_REPLICATED=NOW() WHERE ID=? ", [audit.id])
          }

      }


  }



  def resolveSucursal(def sucursalId){
      def sucursal=Sucursal.get(sucursalId)
  }

}

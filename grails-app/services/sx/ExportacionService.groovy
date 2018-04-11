package sx

import grails.gorm.transactions.Transactional
import groovy.sql.Sql

@Transactional
class ExportacionService {
  def replicaService
   def replicaOperacionService
   def dataSourceLocatorService
   def dataSource



   def exportarClientes(){

      // println " *** Exportando Clientes ***"

       def query="""select * from audit_log where name ='Cliente' and date_replicated is null and date(date_created)>='2018/02/01'"""
       replicaService.exportar(query)

       def query1="""select * from audit_log where name = 'ClienteCredito' and date_replicated is null and date(date_created)>='2018/02/01'"""
       replicaService.exportar(query1)

       def query2="""select * from audit_log where name = 'DireccionDeEntrega' and date_replicated is null and date(date_created)>='2018/02/01'"""
       replicaService.exportar(query2)

       def query3="""select * from audit_log where name = 'ComunicacionEmpresa' and date_replicated is null and date(date_created)>='2018/02/01' order by date_created"""
       replicaService.exportar(query3)

   }

   def exportarVales(){

    //   println " *** Exportando Vales ***"

       def query1="""select * from audit_log where name = 'SolicitudDeTrasladoDet' and date_replicated is null and date(date_created)>='2018/02/01' """
       replicaService.exportar(query1)

   }

   def exportarTraslados(){

   //    println "*** Exportando Traslados ***"

       def query1="""select * from audit_log where name ='TrasladoDet' and date_replicated is null and date(date_created)>='2018/02/01'"""
       replicaService.exportar(query1)
   }

   def exportarPropiedadesClientes() {

    //   println " *** Exportando Propiedades Clientes ***"

       def query="""select * from audit_log where name in ('ClienteContactos','ComunicacionEmpresa','DirecciondeEntrega') and date_replicated is null and date(date_created)>='2018/02/01'"""
       replicaService.exportar(query)

   }

   def exportarExistencias(){

    //   println " *** Exportando Existencias ***"

       def query="""select * from audit_log where name = 'Existencia' and date_replicated is null and date(date_created)>='2018/02/01' and event_name='INSERT' """
       replicaService.exportar(query)
   }

   def exportarSolicitudesDeDepositos(){

       def query1="""select * from audit_log where name ='Cobro' and date_replicated is null and date(date_created)>='2018/02/01'"""
       replicaService.exportar(query1)

       def query2="""select * from audit_log where name ='CobroDeposito' and date_replicated is null and date(date_created)>='2018/02/01'"""
       replicaService.exportar(query2)

       def query3="""select * from audit_log where name ='CobroTransferencia' and date_replicated is null and date(date_created)>='2018/02/01'"""
       replicaService.exportar(query3)

   }

   def exportarProductos(){

    //   println " *** Exportando Producto ***"

       def query="""select * from audit_log where name ='Producto' and date_replicated is null"""

       replicaService.exportar(query)

   }

   def exportarClienteCredito(){

       def query="""select * from audit_log where name ='ClienteCredito' and date_replicated is null """

       replicaService.exportar(query)
   }

   def exportarOperacionesPrincipales(){

       def query="""select * from audit_log where name in ('Cobro','CobroDeposito','CobroTransferencia') and date_replicated is null and date(date_created)>='2018/02/01' """

       replicaService.exportar(query)

   }

   def exportarCatalogos(){

   }

   def exportarSolicitudesDeDepositos1(){

       def query="""select * from audit_log where name ='SolicitudDeDeposito' and date_replicated is null and date(date_created)>='2018/02/01'"""

       def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

       def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

       def datasourceCentral=dataSourceLocatorService.dataSourceLocator(central.server)

       def centralSql=new Sql(datasourceCentral)

       servers.each { server ->

        //   println "***************************************************  Exportando Depositos  a: ${server.server} ******* ${server.url}****  "

           def datasourceTarget=dataSourceLocatorService.dataSourceLocator(server.server)

           def targetSql=new Sql(datasourceTarget)

           centralSql.rows(query+" and target='${server.server}'").each { audit ->
            //   println "--------------------------Exportando Audit--------------  "+audit.persisted_object_id


               def solicitud= centralSql.firstRow("Select * from solicitud_de_deposito where id=?",[audit.persisted_object_id] )

               if(solicitud.cobro_id){
                  // println "Exportando Cobro ${solicitud.cobro_id} a la sucursal${server.server}"
                   def cobro=centralSql.firstRow("Select * from cobro where id=?",[solicitud.cobro_id])
                   replicaOperacionService.exportar('Cobro',cobro.id,false,'INSERT',centralSql,targetSql,datasourceTarget)
               }
               //println "Exportando Solicitud de Deposito a la sucursal ${server.server}"
               replicaOperacionService.exportar(audit.name,audit.persisted_object_id,true,audit.event_name,centralSql,targetSql,datasourceTarget)

           }
       }

   }

   def exportarValesOp(){

     //  println " *** Exportando Vales por el nuevo metodo ***"

       def query="""select * from audit_log where name ='SolicitudDeTraslado' and date_replicated is null and date(date_created)>='2018/02/01'"""

       def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

       def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

       def datasourceCentral=dataSourceLocatorService.dataSourceLocator(central.server)

       def centralSql=new Sql(datasourceCentral)

       servers.each { server ->

         //  println "***  Exportando  a: ${server.server} ******* ${server.url}****  "

           def datasourceTarget=dataSourceLocatorService.dataSourceLocator(server.server)

           def targetSql=new Sql(datasourceTarget)

           centralSql.rows(query+" and target='${server.server}'").each { audit ->
            //   println "audit   "+audit


               def vale= centralSql.firstRow("Select * from solicitud_de_traslado where id=?",[audit.persisted_object_id] )
               def detalles = centralSql.rows("select * from solicitud_de_traslado_det where solicitud_de_traslado_id=?",[audit.persisted_object_id])

               if(vale && detalles.size>0){

                 //  println "Si tiene Registro procedo a exportar"
                   replicaOperacionService.exportar(audit.name,audit.persisted_object_id,true,audit.event_name,centralSql,targetSql,datasourceTarget)

                   detalles.each{ det ->

                   //    println "Exportando detalles***********************************************************"

                       replicaOperacionService.exportar("SolicitudDeTrasladoDet",det.id,false,audit.event_name,centralSql,targetSql,datasourceTarget)
                   }

               }

           }

       }

   }

   def exportarTrasladosOp(){
//        println " *** Exportando Traslados por el nuevo metodo ***"

       def query="""select * from audit_log where name ='Traslado' and date_replicated is null and date(date_created)>='2018/02/01'"""

       def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

       def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

       def datasourceCentral=dataSourceLocatorService.dataSourceLocator(central.server)

               def centralSql=new Sql(datasourceCentral)

               servers.each { server ->

                //   println "***-----------------------------  Exportando  a: ${server.server} ******* ${server.url}****  "

                   def datasourceTarget=dataSourceLocatorService.dataSourceLocator(server.server)

                   def targetSql=new Sql(datasourceTarget)

                   centralSql.rows(query+" and target='${server.server}'").each { audit ->
                     //  println "audit   "+audit


                       def traslado= centralSql.firstRow("Select * from traslado where id=?",[audit.persisted_object_id] )
               def detalles=centralSql.rows("select * from traslado_det where traslado_id=?",[audit.persisted_object_id])

               if(traslado && detalles.size() > 0 ){
                   //println "Si tiene Registro procedo a exportar"

                   def vale=centralSql.firstRow("Select * from solicitud_De_traslado where id=?",[traslado.solicitud_de_traslado_id])
                   def detallesVale=centralSql.rows("Select * from solicitud_de_traslado_det where solicitud_de_traslado_id=?",[traslado.solicitud_de_traslado_id])

                   replicaOperacionService.exportar("SolicitudDeTraslado",vale.id,false,audit.event_name,centralSql,targetSql,datasourceTarget)

                   detallesVale.each{detVale ->
                       replicaOperacionService.exportar("SolicitudDeTrasladoDet",detVale.id,false,audit.event_name,centralSql,targetSql,datasourceTarget)
                   }

                   replicaOperacionService.exportar(audit.name,audit.persisted_object_id,true,audit.event_name,centralSql,targetSql,datasourceTarget)

                    detalles.each{ det ->

                     //  println "Exportando detalles***********************************************************"

                       replicaOperacionService.exportar("TrasladoDet",det.id,false,audit.event_name,centralSql,targetSql,datasourceTarget)
                   }

               }



           }

       }
   }


   def exportarTrasladosSuc(def sucursal){
       println " *** Exportando Traslados por el nuevo metodo ***"

       def query="""select * from audit_log where name ='Traslado' AND event_name='INSERT' and date_replicated is null"""

      // def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

       def server=DataSourceReplica.findByServer(sucursal)

       def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

       def datasourceCentral=dataSourceLocatorService.dataSourceLocator(central.server)

       def centralSql=new Sql(datasourceCentral)

 //      servers.each { server ->

              println "***-----------------------------  Exportando  a: ${server.server} ******* ${server.url}****  "

           def datasourceTarget=dataSourceLocatorService.dataSourceLocator(server.server)

           def targetSql=new Sql(datasourceTarget)

           centralSql.rows(query+" and target='${server.server}'").each { audit ->
               //  println "audit   "+audit


               def traslado= centralSql.firstRow("Select * from traslado where id=?",[audit.persisted_object_id] )
               def detalles=centralSql.rows("select * from traslado_det where traslado_id=?",[audit.persisted_object_id])

               if(traslado && detalles.size() > 0 ){
                   println "Si tiene Registro procedo a exportar"

                   def vale=centralSql.firstRow("Select * from solicitud_De_traslado where id=?",[traslado.solicitud_de_traslado_id])
                   def detallesVale=centralSql.rows("Select * from solicitud_de_traslado_det where solicitud_de_traslado_id=?",[traslado.solicitud_de_traslado_id])

                   replicaOperacionService.exportar("SolicitudDeTraslado",vale.id,false,audit.event_name,centralSql,targetSql,datasourceTarget)

                   detallesVale.each{detVale ->
                       replicaOperacionService.exportar("SolicitudDeTrasladoDet",detVale.id,false,audit.event_name,centralSql,targetSql,datasourceTarget)
                   }

                   replicaOperacionService.exportar(audit.name,audit.persisted_object_id,true,audit.event_name,centralSql,targetSql,datasourceTarget)

                   detalles.each{ det ->

                       //  println "Exportando detalles***********************************************************"

                       replicaOperacionService.exportar("TrasladoDet",det.id,false,audit.event_name,centralSql,targetSql,datasourceTarget)
                   }

               }



           }

    //   }
   }




}

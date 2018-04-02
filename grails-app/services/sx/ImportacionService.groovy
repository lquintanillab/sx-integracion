package sx

import groovy.sql.Sql
import groovy.util.logging.Commons
import org.springframework.dao.DuplicateKeyException
import org.springframework.jdbc.core.simple.SimpleJdbcInsert


class ImportacionService {

    def dataSourceLocatorService
    def replicaService
    def replicaOperacionService

    def importarSolicitudesDepositos(){

    }


    

    def importadorAlmacenOP(){

      //  println "Importando operaciones de Almacen"
        def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

        def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

        def datasourceCentral=dataSourceLocatorService.dataSourceLocator(central.server)

        def centralSql=new Sql(datasourceCentral)

        servers.each { server ->

           println "***  Importando desde: ${server.server} ******* ${server.url}****  "

            def datasourceOrigen=dataSourceLocatorService.dataSourceLocator(server.server)


            def sql=new Sql(datasourceOrigen)

            def query="Select * from audit_log where name= 'inventario' and event_name='INSERT' and date_replicated is null and date(date_created)>='2018/02/01'  "

           // println "Ejecutando Query"

            sql.rows(query).each { audit ->

                def queryInvent= "select * from inventario where id=?"

                def inventario=sql.firstRow(queryInvent,[audit.persisted_object_id])

                println "****************** Inventario "+inventario?.tipo+"   *********************  "+audit.source +" ---------"+audit.id+"--------------"+audit.persisted_object_id

                if(inventario){

                    if(inventario.tipo != 'FAC'){
                        replicaOperacionService.importar( 'Inventario',inventario.id,true,audit.event_name, centralSql, sql,datasourceCentral)

                        //   println "*** *********************************************************************************************************************** Inventario "+inventario.tipo
                        switch (inventario.tipo){

                            case 'RMD':
                                println "*** *********************************************************************************************************************** Inventario "+inventario.tipo

                                def devolucionDet=sql.firstRow("Select * from devolucion_de_venta_det where inventario_id=?",[inventario.id])
                                if(devolucionDet){

                                    def devolucion=sql.firstRow("Select * from devolucion_de_venta where id=?",[devolucionDet.devolucion_de_venta_id])

                                    replicaOperacionService.importar( 'Devolucion',devolucion.id,false,audit.event_name, centralSql, sql,datasourceCentral)

                                    def cobro=sql.firstRow("Select * from Cobro where id=?",[devolucion.cobro_id])

                                    if(cobro){
                                        replicaOperacionService.importar( 'Cobro',cobro.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                                    }
                                    replicaOperacionService.importar( 'DevolucionDet',devolucionDet.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                                }

                                break

                            case 'CIM':
                            case 'CIS':
                            case 'VIR':
                            case 'MER':
                            case 'OIM':
                            case 'RMC':
                                // println "*** *********************************************************************************************************************** Inventario "+inventario.tipo

                                def moviDet=sql.firstRow("Select * from movimiento_de_almacen_det where inventario_id=?",[inventario.id])
                                if(moviDet){
                                    def movi=sql.firstRow("Select * from movimiento_de_almacen where id=?",[moviDet.movimiento_de_almacen_id])
                                    replicaOperacionService.importar( 'MovimientoDeAlmacen',movi.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                                    replicaOperacionService.importar( 'MovimientoDeAlmacenDet',moviDet.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                                }

                                break
                            case 'TRS':
                            case 'REC':
                                // println "*** *********************************************************************************************************************** Inventario "+inventario.tipo

                                def trsDet=sql.firstRow("Select * from transformacion_det where inventario_id=?",[inventario.id])
                                if(trsDet){
                                    def trs=sql.firstRow("Select * from transformacion where id=?",[trsDet.transformacion_id])
                                    replicaOperacionService.importar( 'Transformacion',trs.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                                    replicaOperacionService.importar( 'TransformacionDet',trsDet.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                                }

                                break
                            case 'TPE':
                            case 'TPS':

                            def trdDet=sql.firstRow("Select * from traslado_det where inventario_id=?",[inventario.id])
                            if(trdDet){
                                def trd=sql.firstRow("Select * from traslado where id=?",[trdDet.traslado_id])

                                if(trd){

                                    def cfdi=sql.firstRow("select * from cfdi where id=?",[trd.cfdi_id])

                                    if(cfdi){
                                       //  println "*********************************** Procedo a  importar Cfdi de Traslado "
                                        replicaOperacionService.importar( 'Cfdi',cfdi.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                                    }

                                    //println "**************************************** Procedo a importar Traslado "

                                    replicaOperacionService.importar( 'Traslado',trd.id,false,audit.event_name, centralSql, sql,datasourceCentral)

                                   // println "**************************************** Procedo a importar TrasladoDet"
                                    replicaOperacionService.importar( 'TrasaladoDet',trdDet.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                                }
                            }
                                break
                            default:
                                break

                        }

                    }else{
                        println "Inventario de venta:  "+audit.persisted_object_id
                    }

                }else{
                    println "Quitando  inventario de audit sin registro de informacion"
                    sql.execute("UPDATE AUDIT_LOG SET DATE_REPLICATED=NOW(),MESSAGE=? WHERE PERSISTED_OBJECT_ID=? AND EVENT_NAME=? ", ["REVISAR NO EXISTE EN EL ORIGEN", audit.persisted_object_id,audit.event_name])
                }



            }


        }

    }


    def importarTrasladosOp(){
           println "*******************************************************  Importando operaciones de Traslado****************************************************"
        def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

        def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

        def datasourceCentral=dataSourceLocatorService.dataSourceLocator(central.server)

        def centralSql=new Sql(datasourceCentral)

        servers.each { server ->

            //  println "***  Importando desde: ${server.server} ******* ${server.url}****  "

            def datasourceOrigen = dataSourceLocatorService.dataSourceLocator(server.server)

            def sql = new Sql(datasourceOrigen)

            def query = "Select * from audit_log where name= 'Traslado'  and date_replicated is null  "

             println "Ejecutando Query en ${server.server} --- ${new Date()}"
            sql.rows(query).each { audit ->

                def queryTrd= "select * from Traslado where id=?"

                def trd=sql.firstRow(queryTrd,[audit.persisted_object_id])
                if(trd){

                    def cfdi=sql.firstRow("select * from cfdi where id=?",[trd.cfdi_id])

                    if(cfdi){
                         println " Procedo a  importar Cfdi de Traslado "
                        replicaOperacionService.importar( 'Cfdi',cfdi.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                    }

                   println "Procedo a importar Traslado "
                    replicaOperacionService.importar( 'Traslado',trd.id,false,audit.event_name, centralSql, sql,datasourceCentral)

                    def partidas=sql.rows("select * from traslado_Det where traslado_id=?",[trd.id])

                    partidas.each{trdDet ->

                        if(trdDet){
                            def inventario=sql.firstRow("Select * from inventario where id=?",[trdDet.inventario_id])
                            if(inventario){
                                println "Procedo a importar Inventario "
                                replicaOperacionService.importar( 'Inventario',inventario.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                            }

                            println "Procedo a importar TrasladoDet "
                            replicaOperacionService.importar( 'TrasladoDet',trdDet.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                        }

                    }

                }else{
                     println "Quitando  inventario de audit sin registro de informacion"
                    sql.execute("UPDATE AUDIT_LOG SET DATE_REPLICATED=NOW(),MESSAGE=? WHERE PERSISTED_OBJECT_ID=? AND EVENT_NAME=? ", ["REVISAR NO EXISTE EN EL ORIGEN", audit.persisted_object_id,audit.event_name])
                }


            }
        }
    }

    def importarTrasladosSuc(def sucursal){
        println "*******************************************************  Importando operaciones de Traslado Por Sucursal****************************************************"
       // def servers=DataSourceReplica.findByServer(sucursal)

        def server=DataSourceReplica.findByServer(sucursal)

        def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

        def datasourceCentral=dataSourceLocatorService.dataSourceLocator(central.server)

        def centralSql=new Sql(datasourceCentral)

      //   servers.each { server ->

            //  println "***  Importando desde: ${server.server} ******* ${server.url}****  "

            def datasourceOrigen = dataSourceLocatorService.dataSourceLocator(server.server)

            def sql = new Sql(datasourceOrigen)

            def query = "Select * from audit_log where name= 'Traslado' and event_name='INSERT'  and date_replicated is null  "

            println "Ejecutando Query en ${server.server} --- ${new Date()}"
            sql.rows(query).each { audit ->

                def queryTrd= "select * from Traslado where id=?"

                def trd=sql.firstRow(queryTrd,[audit.persisted_object_id])
                if(trd){

                    def cfdi=sql.firstRow("select * from cfdi where id=?",[trd.cfdi_id])

                    if(cfdi){
                        println " Procedo a  importar Cfdi de Traslado "
                        replicaOperacionService.importar( 'Cfdi',cfdi.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                    }

                    println "Procedo a importar Traslado "
                    replicaOperacionService.importar( 'Traslado',trd.id,false,audit.event_name, centralSql, sql,datasourceCentral)

                    def partidas=sql.rows("select * from traslado_Det where traslado_id=?",[trd.id])

                    partidas.each{trdDet ->

                        if(trdDet){
                            def inventario=sql.firstRow("Select * from inventario where id=?",[trdDet.inventario_id])
                            if(inventario){
                                println "Procedo a importar Inventario "
                                replicaOperacionService.importar( 'Inventario',inventario.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                            }

                            println "Procedo a importar TrasladoDet "
                            replicaOperacionService.importar( 'TrasladoDet',trdDet.id,false,audit.event_name, centralSql, sql,datasourceCentral)
                        }

                    }

                }else{
                    println "Quitando  inventario de audit sin registro de informacion"
                    sql.execute("UPDATE AUDIT_LOG SET DATE_REPLICATED=NOW(),MESSAGE=? WHERE PERSISTED_OBJECT_ID=? AND EVENT_NAME=? ", ["REVISAR NO EXISTE EN EL ORIGEN", audit.persisted_object_id,audit.event_name])
                }


            }
      //  }
    }

}

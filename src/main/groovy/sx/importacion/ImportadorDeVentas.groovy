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
class ImportadorDeVentas{


  @Autowired
   @Qualifier('dataSourceLocatorService')
  def dataSourceLocatorService
  @Autowired
  @Qualifier('dataSource')
  def dataSource

      def importar(){
          importar(new Date())
      }
      def importar(fecha){
        println ("Importando Ventas del : ${fecha.format('dd/MM/yyyy')}" )

        def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)

          def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

          servers.each(){server ->

            println "***  Importando de Por ReplicaService: ${server.server} ******* ${server.url}****  "
            importarServerFecha(server,fecha)
          }
      }


      def importarSucursalFecha(nombreSuc,fecha){

        def server=DataSourceReplica.findByServer(nombreSuc)

        println "Importando Ventas nombre: ${nombreSuc} fecha: ${fecha.format('dd/MM/yyyy')} URL: ${server} "

      importarServerFecha(server,fecha)

      }

      def importarServerFecha(server,fecha){
        println "Importando Por Server Fecha de Ventas "
        def dataSourceSuc=dataSourceLocatorService.dataSourceLocatorServer(server)
        def sqlSuc=new Sql(dataSourceSuc)
        def sqlCen=new Sql(dataSource)

        def config= EntityConfiguration.findByName("Cfdi")

        def query = "select * from cfdi where tipo_de_comprobante='I' and date(date_created)=?"
        def queryId="select * from cfdi where id=?"

        sqlSuc.rows(query,[fecha.format('yyyy/MM/dd')]).each{row ->

            def found=sqlCen.firstRow(queryId,[row.id])

            if(found){
               println "EL registro ya fue importado Solo actualizar"
              sqlCen.executeUpdate(row, config.updateSql)

            }else{
               println "El registro no ha sido importado se debe importar"
              SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("ficha")
               def res=insert.execute(row)
            }

            def cxcSuc=sqlSuc.firstRow("select * from cuenta_por_cobrar where cfdi_id=?",[row.id])
            def cxcCen=sqlCen.firstRow("select * from cuenta_por_cobrar where id=?",[cxcSuc.id])
            def configCxc=EntityConfiguration.findByName("CuentaPorCobrar")

            if(cxcCen){
                println "El registro de cxc ya fue importado solo se debe actulizar"
                sqlCen.executeUpdate(cxcSuc, configCxc.updateSql)
            }else{
               println "El registro de cxc no se ha importado se debe importar"
                 SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("cuenta_por_cobrar")
                  def res=insert.execute(cxcSuc)
            }

            def vtaSuc=sqlSuc.firstRow("select * from venta where cuenta_por_cobrar_id=?",[cxcSuc.id])
            def vtaCen=sqlCen.firstRow("select * from venta where id=?",[vtaSuc.id])
            def configVta=EntityConfiguration.findByName("Venta")

            if(vtaCen){
              println "El registro de venta ya fue importado solo se debe actulizar"
                sqlCen.executeUpdate(vtaSuc, configVta.updateSql)
            }else{
              println "El registro de cxc no se ha importado se debe importar"
                SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("venta")
                def res=insert.execute(vtaSuc)
            }

        }

      }

  }

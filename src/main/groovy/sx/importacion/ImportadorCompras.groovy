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
class ImportadorDeCompras {


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

      def config= EntityConfiguration.findByName("Compra")
      def configDet= EntityConfiguration.findByName("CompraDet")

      def querySuc="Select * from compra where fecha=?"

      def compras=sqlSuc.rows(querySuc,[fecha.format('yyyy/MM/dd')])

      compras.each{ compraSuc ->

        println compraSuc

        def queryCen="Select * from compra where id=?"
        def compraCen=sqlCen.firstRow(queryCen,[compraSuc.id])

            if(compraCen){
              println "EL registro de compra ya fue importado Solo actualizar"
                sqlCen.executeUpdate(compraSuc, config.updateSql)
            }else{

              println "El registro de compra no ha sido importado se debe importar"

              SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("compra")
              def res=insert.execute(compraSuc)

            }

            def queryPartidas="Select * from compra_det where compra_id=?"
            def partidas=sqlSuc.rows(queryPartidas,[compraSuc.id])

                partidas.each{partidaSuc ->

                  def queryPartidaCen="Select * from compra_det where id=?"
                  def partidaCen=sqlCen.firstRow(queryPartidaCen,[partidaSuc.id])

                  if(partidaCen){
                    println "EL registro de partida ya fue importado Solo actualizar"
                    sqlCen.executeUpdate(partidaSuc, configDet.updateSql)
                  }else{
                    println "El registro de partida no ha sido importado se debe importar"
                    SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("compra_det")
                    def res=insert.execute(partidaSuc)
                  }
            }
        }
    }

}

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
class ImportadorDeEmbarques{

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
    //  println ("Importando Embarques del : ${fecha.format('dd/MM/yyyy')}" )

      def servers=DataSourceReplica.findAllByActivaAndCentral(true,false)
      def central=DataSourceReplica.findAllByActivaAndCentral(true,true)

        servers.each(){server ->
    //      println "***  Importando de Por ReplicaService: ${server.server} ******* ${server.url}****  "
          importarServerFecha(server,fecha)
        }
    }


    def importarSucursalFecha(nombreSuc,fecha){

      def server=DataSourceReplica.findByServer(nombreSuc)

    //  println "nombre: ${nombreSuc} fecha: ${fecha.format('dd/MM/yyyy')} URL: ${server.url} "

      importarServerFecha(server,fecha)

    }

    def importarServerFecha(server,fecha){
    //  println "Importando Por Server Fecha   "+fecha+ "   "+server.server
      def dataSourceSuc=dataSourceLocatorService.dataSourceLocatorServer(server)
      def sqlSuc=new Sql(dataSourceSuc)
      def sqlCen=new Sql(dataSource)

      def queryEmbSuc="select * from embarque where date(fecha)=?"

      def embarquesSuc=sqlSuc.rows(queryEmbSuc,[fecha])

      def queryEmb="Select * from embarque where id=?"

      embarquesSuc.each{embarqueSuc ->
            def embarqueCen=sqlCen.firstRow(queryEmb,[embarqueSuc.id])
            if(!embarqueCen){
          //    println "Importando Embarque"+ embarqueSuc.id
              SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("embarque")
              def res=insert.execute(embarqueSuc)
            }else{
          //    println "Ya Importado Embarque"+ embarqueSuc.id
            }

            def queryEnvios="Select * from envio where embarque_id=?"
            def enviosSuc=sqlSuc.rows(queryEnvios,[embarqueSuc.id])

            enviosSuc.each{envioSuc ->
                  def queryEnvio="Select * from envio where id=?"
                  def envioCen=sqlCen.firstRow(queryEnvio,[envioSuc.id])
                  if(!envioCen){
                //    println "Importando Envio"+ envioSuc.id
                    SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("envio")
                    def res=insert.execute(envioSuc)
                  }else{
                //    println "Ya Importado Envio"+ envioSuc.id
                  }

                  def queryEnviosDet="select * from envio_det where envio_id=?"
                  def enviosDet=sqlSuc.rows(queryEnviosDet,[envioSuc.id])

                  enviosDet.each{ envioDetSuc ->
                        def queryEnvioDet="Select * from envio_det where id=? "
                        def envioDetCen=sqlCen.firstRow(queryEnvioDet,[envioDetSuc.id])
                        if(!envioDetCen){
                    //      println "Importando EnvioDet"+ envioDetSuc.id
                          SimpleJdbcInsert insert=new SimpleJdbcInsert(dataSource).withTableName("envio_det")
                          def res=insert.execute(envioDetSuc)
                        }else{
                      //    println "Ya Importado EnvioDet"+ envioDetSuc.id
                        }
                  }
            }
      }


    }

}

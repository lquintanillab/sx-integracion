package sx.importacion

class ImportadorDeBolivarJob {


    def importadorDeVales
    def importadorDeTraslados
    def replicaService

      static triggers = {
      cron name:   'impBolivar',   startDelay: 20000, cronExpression: '0 0/4 * * * ?'
      }

      def execute() {

       println "************************************************************"
       println "*                                                          *"
       println "*                    Importando Bolivar  ${new Date()}                   *"
       println "*                                                          *"
       println "************************************************************"

       def sucursal = 'BOLIVAR'

       try{
      //    println "importando Vales: "+sucursal
          importadorDeVales.importarSucursal(sucursal)
       }catch (Exception e){
              e.printStackTrace()
      }
      try{
      //   println "importando Traslados: "+sucursal
         importadorDeTraslados.importarSucursal(sucursal)
      }catch (Exception e){
             e.printStackTrace()
     }

     try{
        println "******************************importando Embarques********************************** "+sucursal
        replicaService.importarServer(sucursal)
     }catch (Exception e){
            e.printStackTrace()
    }

      }
}

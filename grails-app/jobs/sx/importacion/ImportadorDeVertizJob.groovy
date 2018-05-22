package sx.importacion

class ImportadorDeVertizJob {

      def importadorDeExistencias
      def importadorDeClientes
      def importadorDeVales
      def importadorDeTraslados

        static triggers = {
        cron name:   'impVertiz',   startDelay: 20000, cronExpression: '0 0/4 * * * ?'
        }

        def execute() {

         println "************************************************************"
         println "*                                                          *"
         println "*                    Importando Vertiz  ${new Date()}                    *"
         println "*                                                          *"
         println "************************************************************"

         def sucursal = 'VERTIZ 176'
         try{
          //  println "importando Vales: "+sucursal
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


        }
}

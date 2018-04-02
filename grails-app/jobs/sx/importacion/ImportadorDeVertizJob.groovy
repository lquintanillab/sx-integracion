package sx.importacion

class ImportadorDeVertizJob {

      def importadorDeExistencias
      def importadorDeClientes

        static triggers = {
        cron name:   'impVertiz',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
        }

        def execute() {
         println "************************************************************"
         println "*                                                          *"
         println "*                    Importando Vertiz                     *"
         println "*                                                          *"
         println "************************************************************"

         def sucursal = 'VERTIZ 176'

         try{
            importadorDeExistencias.importar(sucursal)
         }catch (Exception e){
                e.printStackTrace()
        }
        try{
           importadorDeClientes.importar(sucursal)
        }catch (Exception e){
               e.printStackTrace()
       }


        }
}

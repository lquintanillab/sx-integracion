package sx.importacion

class ImportadorDeTacubaJob {

      def importadorDeExistencias
      def importadorDeClientes

        static triggers = {
        cron name:   'impTacuba',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
        }

        def execute() {
         println "************************************************************"
         println "*                                                          *"
         println "*                    Importando Tacuba                     *"
         println "*                                                          *"
         println "************************************************************"

         def sucursal = 'TACUBA'

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

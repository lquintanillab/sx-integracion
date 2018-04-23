package sx.importacion

class ImportadorCalle4Job {

      def importadorDeExistencias
      def importadorDeClientes
      def importadorDeVales
      def importadorDeTraslados

        static triggers = {
        cron name:   'impCalle42',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
        }

        def execute() {

         println "************************************************************"
         println "*                                                          *"
         println "*                    Importando Calle 4 Dos                  *"
         println "*                                                          *"
         println "************************************************************"

         def sucursal = 'CALLE 4'

         try{
           println "importando Existencias: "+sucursal
            importadorDeExistencias.importar(sucursal)
         }catch (Exception e){
                e.printStackTrace()
        }
        try{
           println "importando Clientes: "+sucursal
           importadorDeClientes.importar(sucursal)
        }catch (Exception e){
               e.printStackTrace()
       }
       try{
          println "importando ComunicacionEmpresa: "+sucursal
          importadorDeClientes.importarComunicacionEmpresa(sucursal)
       }catch (Exception e){
              e.printStackTrace()
      }

        }
}

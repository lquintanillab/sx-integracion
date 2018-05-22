package sx.importacion

class ImportadorDeTacubaJob {

        def importadorDeExistencias
        def importadorDeClientes
        def importadorDeVales
        def importadorDeTraslados

          static triggers = {
          cron name:   'impTacuba',   startDelay: 20000, cronExpression: '0 0/4 * * * ?'
          }

          def execute() {

           println "************************************************************"
           println "*                                                          *"
           println "*                    Importando Tacuba  ${new Date()}                    *"
           println "*                                                          *"
           println "************************************************************"

           def sucursal = 'TACUBA'

           try{
            //  println "importando Vales: "+sucursal
              importadorDeVales.importarSucursal(sucursal)
           }catch (Exception e){
                  e.printStackTrace()
          }
          try{
        //     println "importando Traslados: "+sucursal
             importadorDeTraslados.importarSucursal(sucursal)
          }catch (Exception e){
                 e.printStackTrace()
         }

        }
}

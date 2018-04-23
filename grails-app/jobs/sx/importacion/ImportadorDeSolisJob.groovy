package sx.importacion

class ImportadorDeSolisJob {

    def importadorDeExistencias
    def importadorDeClientes
    def importadorDeVales
    def importadorDeTraslados

      static triggers = {
      cron name:   'impSolis',   startDelay: 20000, cronExpression: '0 0/4 * * * ?'
      }

      def execute() {

       println "************************************************************"
       println "*                                                          *"
       println "*                    Importando Solis                      *"
       println "*                                                          *"
       println "************************************************************"

       def sucursal = 'SOLIS'

       try{
          println "importando Vales: "+sucursal
          importadorDeVales.importarSucursal(sucursal)
       }catch (Exception e){
              e.printStackTrace()
      }
      try{
         println "importando Traslados: "+sucursal
         importadorDeTraslados.importarSucursal(sucursal)
      }catch (Exception e){
             e.printStackTrace()
     }

      }
}

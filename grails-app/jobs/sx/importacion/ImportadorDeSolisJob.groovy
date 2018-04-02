package sx.importacion

class ImportadorDeSolisJob {

    def importadorDeExistencias
    def importadorDeClientes

      static triggers = {
      cron name:   'impSolis',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {
       println "************************************************************"
       println "*                                                          *"
       println "*                    Importando Solis                      *"
       println "*                                                          *"
       println "************************************************************"

       def sucursal = 'SOLIS'

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

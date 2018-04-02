package sx.importacion

class ImportadorDeBolivarJob {

    def importadorDeExistencias
    def importadorDeClientes

      static triggers = {
      cron name:   'impBolivar',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {
       println "************************************************************"
       println "*                                                          *"
       println "*                    Importando Bolivar                    *"
       println "*                                                          *"
       println "************************************************************"

       def sucursal = 'BOLIVAR'

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

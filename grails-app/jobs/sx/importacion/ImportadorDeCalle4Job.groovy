package sx.importacion

class ImportadorDeCalle4Job {

    def importadorDeExistencias
    def importadorDeClientes

      static triggers = {
      cron name:   'impCalle4',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {
       println "************************************************************"
       println "*                                                          *"
       println "*                    Importando Calle 4                    *"
       println "*                                                          *"
       println "************************************************************"

       def sucursal = 'CALLE 4'

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

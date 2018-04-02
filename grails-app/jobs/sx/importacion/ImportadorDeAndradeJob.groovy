package sx.importacion

class ImportadorDeAndradeJob {

    def importadorDeExistencias
    def importadorDeClientes

      static triggers = {
      cron name:   'impAndrade',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {
       println "************************************************************"
       println "*                                                          *"
       println "*                    Importando Andrade                    *"
       println "*                                                          *"
       println "************************************************************"

       def sucursal = 'ANDRADE'

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

package sx

class ImportadorDeComprasJob {

    def importadorDeCompras

    static triggers = {
      cron name:   'impCompras',   startDelay: 10000, cronExpression: '0 0/20 * * * ?'
    }

    def execute() {
        
      println "************************************************************"
      println "*                                                          *"
      println "*         Importador De Compras       ${new Date()}  "
      println "*                                                          *"
      println "************************************************************"

      try{
        //importadorDeCompras.importar()
      }catch(Exception e){
        e.printStackTrace()
      }


    }
}

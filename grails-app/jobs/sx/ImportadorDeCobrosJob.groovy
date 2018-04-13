package sx

class ImportadorDeCobrosJob {
  def importadorDeCobros
    static triggers = {
       cron name:   'impCobros',   startDelay: 10000, cronExpression: '0 30 19 * * ?'
    }

    def execute() {
/*
      println "************************************************************"
      println "*                                                          *"
      println "*                    Importador De Cobros                  *"
      println "*                                                          *"
      println "************************************************************"
      try{
        importadorDeCobros.importar()
      }catch(Exception e){
        e.printStackTrace()
      }
*/

    }
}

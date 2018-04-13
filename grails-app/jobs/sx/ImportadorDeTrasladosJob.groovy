package sx

class ImportadorDeTrasladosJob {

  def importadorDeTraslados

    static triggers = {
        cron name:   'expTraslados',   startDelay: 20000, cronExpression: '0 0/15 * * * ?'
    }

    def execute() {
/*
      println "************************************************************"
      println "*                                                          *"
      println "*                    Importando Traslados                  *"
      println "*           ${new Date()}                   *"
      println "*                                                          *"
      println "************************************************************"
        try{
            importadorDeTraslados.importar()
        }catch(Exception e ){
          e.printStackTrace()
        }
*/
    }
}

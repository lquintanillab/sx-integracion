package sx

class ExportadorDeTrasladosJob {

def exportadorDeTraslados

    static triggers = {
      cron name:   'expTraslados',   startDelay: 20000, cronExpression: '0 0/15 * * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Traslados                  *"
      println "*           ${new Date()}                   *"
      println "*                                                          *"
      println "************************************************************"
        try{
            exportadorDeTraslados.exportar()
        }catch(Exception e ){
          e.printStackTrace()
        }

    }
}

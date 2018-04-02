package sx.exportacion

class ExportadorDeSolisJob {
    static triggers = {
      cron name:   'expSolis',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Solis                      *"
      println "*                                                          *"
      println "************************************************************"
    }
}

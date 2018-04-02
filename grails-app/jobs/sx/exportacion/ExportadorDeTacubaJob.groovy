package sx.exportacion

class ExportadorDeTacubaJob {
    static triggers = {
      cron name:   'expTacuba',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Tacuba                     *"
      println "*                                                          *"
      println "************************************************************"
    }
}

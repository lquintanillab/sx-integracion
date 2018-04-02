package sx.exportacion

class ExportadorDeBolivarJob {
    static triggers = {
      cron name:   'expBolivar',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Bolivar                    *"
      println "*                                                          *"
      println "************************************************************"
    }
}

package sx.exportacion

class ExportadorDeAndradeJob {
    static triggers = {
      cron name:   'expAndrade',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Andrade                    *"
      println "*                                                          *"
      println "************************************************************"
    }
}

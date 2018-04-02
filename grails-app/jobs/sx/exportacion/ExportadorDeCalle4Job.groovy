package sx.exportacion

class ExportadorDeCalle4Job {
    static triggers = {
    cron name:   'expCalle4',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando Calle 4                    *"
      println "*                                                          *"
      println "************************************************************"
    }
}

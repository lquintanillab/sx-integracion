package sx.exportacion

class ExportadorDe5FebreroJob {
    static triggers = {
      cron name:   'exp5Febrero',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                    Exportando 5 Febrero                  *"
      println "*                                                          *"
      println "************************************************************"
    }
}

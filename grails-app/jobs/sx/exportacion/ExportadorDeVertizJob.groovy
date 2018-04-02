package sx.exportacion

class ExportadorDeVertizJob {
    static triggers = {
      cron name:   'expVertiz',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
    }

    def execute() {
      println "************************************************************"
      println "*                                                          *"
      println "*                   Ex portando Vertiz                     *"
      println "*                                                          *"
      println "************************************************************"
    }
}

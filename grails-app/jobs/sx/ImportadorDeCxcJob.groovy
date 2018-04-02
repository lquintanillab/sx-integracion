package sx

class ImportadorDeCxcJob {

  def importadorDeCxc

    static triggers = {
       cron name:   'impCxc',   startDelay: 10000, cronExpression: '0 0 19 * * ?'
    }

    def execute() {
      importadorDeCxc.importarOperacionesVenta()
    }
}

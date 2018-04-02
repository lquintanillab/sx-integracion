package sx

class ImportadorDeFichasJob {

  def importadorDeFichas

    static triggers = {
      cron name:   'impFichas',   startDelay: 10000, cronExpression: '0 0 19 * * ?'
    }

    def execute() {

      println "************************************************************"
      println "*                                                          *"
      println "*                    Importador De Fichas                  *"
      println "*                                                          *"
      println "************************************************************"

      importadorDeFichas.importar()
    }
}

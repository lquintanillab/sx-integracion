package sx.exportacion

class ExportadorAndradeJob {

    def exportadorDeClientes
    def exportadorDeClientesCredito
    def exportadorDeProductos
    def exportadorDeExistencia
    def exportadorDeVales
    def exportadorDeTraslados

      static triggers = {
        cron name:   'expAndrade2',   startDelay: 10000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {

        println "************************************************************"
        println "*                                                          *"
        println "*                    Exportando Andrade  Dos               *"
        println "*                                                          *"
        println "************************************************************"

        def sucursal = 'ANDRADE'

              try{
                 exportadorDeClientesCredito.exportar(sucursal)
              }catch (Exception e){
                     e.printStackTrace()
             }
             try{
                exportadorDeClientes.exportar(sucursal)
             }catch (Exception e){
                    e.printStackTrace()
              }
              try{
                 exportadorDeProductos.exportar(sucursal)
              }catch (Exception e){
                     e.printStackTrace()
             }
             try{
                exportadorDeExistencia.exportar(sucursal)
             }catch (Exception e){
                    e.printStackTrace()
            }

      }
}

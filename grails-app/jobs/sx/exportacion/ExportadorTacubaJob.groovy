package sx.exportacion

class ExportadorTacubaJob {

    def exportadorDeClientes
    def exportadorDeClientesCredito
    def exportadorDeProductos
    def exportadorDeExistencia
    def exportadorDeVales
    def exportadorDeTraslados

      static triggers = {
        cron name:   'expTacuba2',   startDelay: 20000, cronExpression: '0 0/5 * * * ?'
      }

      def execute() {

        println "************************************************************"
        println "*                                                          *"
        println "*                    Exportando Tacuba Dos   ${new Date()}               *"
        println "*                                                          *"
        println "************************************************************"
        def sucursal = 'TACUBA'

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

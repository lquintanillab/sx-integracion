package sx

class DataSourceReplica {


    String ip

    String server

    String url

    String username

    String password

    String dataBase

    Boolean central = false

    Boolean activa = true



    static constraints = {
        ip nullable : true
        server nullable: true
        url nullable: true
        username nullable: true
        password nullable: true
        dataBase nullable: true

    }
}

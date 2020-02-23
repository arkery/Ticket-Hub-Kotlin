package io.github.arkery.sql

import io.github.arkery.TicketHub
import java.io.File
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.logging.Level


class SQLitedDB(dbLocation: File, pluginInstance: TicketHub){

    private val dbLocation: File = dbLocation
    private val pluginInstance: TicketHub = pluginInstance
    private val driver = "org.sqlite.JDBC"
    private val dbConnectLoc = "jdbc:sqlite:$dbLocation"
    private var dbConnection: Connection? = null

    private val sqlTicketTable = """
            CREATE TABLE IF NOT EXISTS TICKETS(
            ticket_number INT NOT NULL,
            ticket_title varchar(30) NOT NULL,
            ticket_description varchar(500) NOT NULL,
            ticket_owner_uuid varchar(40) NOT NULL,
            PRIMARY KEY ('ticket_number')
            )"""


    /**
     * Connect and if needed, create an SQLite Data base
     *
     * @throws ClassNotFoundException occurs if missing JDBC Driver
     * @throws IOException occurs if failed to create plugin folder
     * @throws SQLException occurs if failed to connect/create Data base
     */
    fun connectDBSQLite() {
        try{
            if(!pluginInstance.dataFolder.exists()){
                pluginInstance.dataFolder.mkdir()
            }
            if(!dbLocation.exists()){
                dbLocation.createNewFile()
            }

            Class.forName(driver)
            this.dbConnection = DriverManager.getConnection(dbConnectLoc)

        }catch (e: ClassNotFoundException){
            pluginInstance.logger.log(Level.SEVERE, "[TicketHub] Missing JDBC Driver")
        }catch (f: IOException){
            pluginInstance.logger.log(Level.SEVERE, "[TicketHub] Unable to create plugin folder")
        }catch (g: SQLException){
            pluginInstance.logger.log(Level.SEVERE, "[TicketHub] Unable to connect to Database")
        }
    }

    /**
     * Create Table to store tickets in Data base
     *
     * @throws SQLException thrown if there was no usable connection (connection might not exist)
     */
    fun createDBTables(){
        val dbQuery = this.dbConnection?.createStatement()
        try{
            dbQuery?.executeUpdate(this.sqlTicketTable)
            dbQuery?.close()
            this.dbConnection?.close()
        }catch (e: SQLException){
            e.printStackTrace()
        }
    }

}
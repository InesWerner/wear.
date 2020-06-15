import cython
import pymssql
from abc import ABC, abstractmethod


server = 'wearsqlserver.database.windows.net'
database = 'wearDB'
username = 'wearbabo'
userserver = 'wearbabo@wearsqlserver.database.windows.net'
password = '24wear-8'

class Connection(ABC):
    """
    Abstrakte Basisklasse, zur Herstellung
    """
    _conn = None

    def __init__(self):
        super().__init__()
        self.__get_connection()

    def __get_connection(self):
        if self._conn is None:
            try:
                self._conn = pymssql.connect(server=server,user=userserver,password=password,database=database)
                print("Azure SQL DB Connection runs!")
            except pymssql.Error as e:
                print("Failed to connect to bank.db: ".format(e))
        return self._conn


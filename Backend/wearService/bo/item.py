class Item:
    def __init__(self):
        self._itemID = ""
        self._name = ""
        self._category = ""
        self._description = ""
        self._numberOfUsage = 0
        self._status = 0
        self._size = ""
        self._color = ""

    def getItemID(self):
        return self._itemID

    def setItemID(self,value):
        self._itemID = value

    def getName(self):
        return self._name

    def setName(self, value):
        self._name = value

    def getCategory(self):
        return self._category

    def setCategory(self, value):
        self._category = value

    def getDescription(self):
        return self._description

    def setDescription(self, value):
        self._description = value

    def getNumberOfUsage(self):
        return self._numberOfUsage

    def setNumberOfUsage(self, value):
        self._numberOfUsage = value

    def getStatus(self):
        return self._status

    def setStatus(self,value):
        self._status = value

    def getSize(self):
        return self._size

    def setSize(self, value):
        self._size = value

    def getColor(self):
        return self._color

    def setColor(self, value):
        self._color = value

    def __str__(self):
        print(self._itemID, self._name, self._category, self._description, self._numberOfUsage, self._status, self._size, self._color)
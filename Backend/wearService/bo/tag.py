class Tag:

    def __init__(self):
        self._tagID = 0
        self._tagStatus = 0

    def setTagID(self,value):
        self._tagID = value

    def getTagID(self):
        return self._tagID

    def setTagStatus(self,value):
        self._tagStatus = value

    def getTagStatus(self):
        return self._tagStatus

    def __str__(self):
        print(self._tagID,self._tagStatus)

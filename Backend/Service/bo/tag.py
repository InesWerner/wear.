class Tag:

    def __init__(self):
        self._tagID = 0
        self._tagKey = 0
        self._tagFree = 0

    def setTagID(self,value):
        self._tagID = value

    def getTagID(self):
        return self._tagID

    def setTagKey(self,value):
        self._tagKey = value;

    def getTagKey(self):
        return self._tagKey

    def getTagFree(self):
        return self._tagFree

    def setTagFree(self, value):
        self._tagFree = value;


    def __str__(self):
        print(self._tagID,self._tagKey, self._tagFree)

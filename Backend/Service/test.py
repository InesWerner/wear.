from bo.tag import Tag
from database.tag_mapper import TagMapper

tagMapper = TagMapper();



tag = tagMapper.findByTagID(98322)

tagMapper.updateByTagID(98322)

print(tag.getTagID(), tag.getTagKey(), tag.getTagFree())

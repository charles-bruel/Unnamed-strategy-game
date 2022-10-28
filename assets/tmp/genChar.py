def split(word): 
    return [char for char in word]
def getLine(name):
    return "CHAR_"+name+" = new TextCharacter('"+name+"', TextureManager.ICON_"+name+", 1, TextCharacter.TextCharacterType.CHARACTER),"
mypath = "C:\\dev\\Java\\Strategy Game\\Strategy Game\\assets\\textures\\icons\\text"
alpha = split("qwertyuiopasdfghjklzxcvbnm")
from os import listdir
from os.path import isfile, join
onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]
for a in alpha:
    print(getLine(a))
    print(getLine(a.upper()))
for f in onlyfiles:
    fn = f.split(".")[0]
    vn = fn.replace("-", "_").upper()
    fna = split(fn)
    if len(fna) == 2:
        if fna[0] == "l" or fna[0] == "u":
            continue
    print(getLine(vn))

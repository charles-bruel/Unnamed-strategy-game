def split(word): 
    return [char for char in word]
def getLine(name, fileName):
    return "ICON_"+name.upper()+" = new Texture(\"icons/text/"+fileName+"\", ICONS),"
mypath = "C:\\dev\\Java\\Strategy Game\\Strategy Game\\assets\\textures\\icons\\text"
from os import listdir
from os.path import isfile, join
onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]
for f in onlyfiles:
    fn = f.split(".")[0]
    vn = fn.replace("-", "_")
    fna = split(fn)
    if len(fna) == 2:
        if fna[0] == "l" or fna[0] == "u":
            continue
    print(getLine(vn, fn))

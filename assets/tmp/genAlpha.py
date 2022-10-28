def split(word): 
    return [char for char in word]
alpha = split("qwertyuiopasdfghjklzxcvbnm")
index = 0
cap = 0
def nextLine(letter):
    global cap
    global index
    global alpha
    ul = ""
    if(cap == 0):
        ul = "l"
    else:
        ul = "u"
    name = "ICON_" + alpha[index]
    if(cap == 1):
        name = name.upper()
    ret = name+" = new Texture(\"icons/text/"+ul+alpha[index]+"\", ICONS),"
    if(cap == 0):
        cap = 1
    else:
        cap = 0
        index += 1
    return ret
while(index < len(alpha)):
    print(nextLine(alpha[index]))

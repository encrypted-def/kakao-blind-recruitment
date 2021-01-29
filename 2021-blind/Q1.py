import string
def solution(new_id):
    id1 = new_id.lower()
    
    step2_filter = string.digits + string.ascii_lowercase + '-_.'
    id2 = ''
    for c in id1:
        if c in step2_filter: id2 += c
    
    id3 = ''
    for c in id2:
        if c != '.': id3 += c
        else:
            if id3 and id3[-1] == '.': continue
            id3 += c

    id4 = id3.strip('.')
    
    id5 = id4[:]
    if not id5: id5='a'
    
    id6 = id5[:15]
    if id6[-1]=='.': id6 = id6[:-1]
    
    id7 = id6[:]
    while len(id7) < 3: id7 = id7 + id7[-1]
    return id7

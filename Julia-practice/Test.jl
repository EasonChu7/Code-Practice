A = [0, -1, 1;
     1, 0, -1;
     -1, 1, 0]

A[1, 2]

function shape(x)
    if x == paper
        return "布"
    elseif x == scissor
        return "剪刀"
    elseif x == stone
        return "石頭"
    end
end


function shape(y)
    if x == paper
        return "布"
    elseif x == scissor
        return "剪刀"
    elseif x == stone
        return "石頭"
    end
end

win = A[x+1, y+1]

x = shape(x)
y = shape(y)
if win == 0
    println("平手")
else
    println("你出" * x) 
    println("電腦出" * y)
    if win == 1
        println("你贏了")
    else
        println("電腦贏了")
    end
end
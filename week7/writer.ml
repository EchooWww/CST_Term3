let return x = (x, "")

let (>>=) (x, s) f =
  let (y, s') = f x in
  (y, s ^ s')

let (>>) mx my = mx >>= fun _ -> my

let inc x = (x + 1, "inc " ^ string_of_int x ^ " ")
let dec x = (x - 1, "dec " ^ string_of_int x ^ " ")

let tell s = ((), s) 

let rec gcd a b = 
  if b = 0 then a 
  else gcd b (a mod b)

let rec gcd' a b = 
  if b = 0 then tell ("gcd is "^ string_of_int a) >> return a
  else tell (Printf.sprintf "gcd' %d %d; " a b) >> gcd' b (a mod b)
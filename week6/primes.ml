type 'a infstream = Cons of 'a * (unit -> 'a infstream)

let rec from n = Cons (n, (fun () -> from (n+1)))

let hd (Cons(h,t)) = h
let tl (Cons(h,t)) = t()

let rec take n s = 
  if n <= 0 then []
  else hd s::take (n-1) (tl s)

let rec del x s =
  if x = hd s then tl s
  else del x (tl s)

let rec filter f s = 
  if f (hd s) then Cons((hd s), fun() -> filter f (tl s))
  else filter f (tl s)

let rec primes = 
  let rec seive s = 
    Cons(hd s, (fun() -> filter (fun x -> x mod (hd s) <> 0) (tl s)))
  in seive (from 2)
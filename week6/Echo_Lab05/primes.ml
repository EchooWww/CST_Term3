type 'a infstream = Cons of 'a * (unit -> 'a infstream)

let rec from n = Cons (n, (fun () -> from (n+1)))

let hd (Cons(h,t)) = h
let tl (Cons(h,t)) = t()

let rec take n s = 
  if n <= 0 then []
  else hd s::take (n-1) (tl s)

(** [filter f s] return a new stream of elements from [s] that satisfy [f] *)
let rec filter f s = 
  if f (hd s) then Cons((hd s), fun() -> filter f (tl s))
  else filter f (tl s)

(** [primes] return a new stream of prime numbers using the sieve of Eratosthenes *)
let primes = 
  let rec seive s = 
    Cons(hd s, fun () -> seive (filter (fun x -> x mod (hd s) <> 0) (tl s)))
  in seive (from 2)
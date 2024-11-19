type ('a, 's) t = State of ('s -> 'a * 's)
let run_state (State f) s = f s
let state f = State f
let return x = state (fun s -> (x, s))
let (>==) m f =
  State (fun s ->
      let (x, s') = run_state m s in
      run_state (f x) s')

let (>>) m1 m2 = m1 >== fun _ -> m2

let (let*) = (>==)

let push x s = x::s
let pop s =
  match s with
  | [] -> failwith "empty stack"
  | x::xs -> xs

let top s =
  match s with
  | [] -> failwith "empty stack"
  | x::xs -> x

let pop' = State (fun s -> ((), pop s))
let push' x = State (fun s -> ((), push x s))
let top' = State (fun s -> (top s, s))

let ex1 = pop' >> pop'>> top' >>= fun x -> pop' >> top' >>= fun y -> return (x + y)
let ex2 = pop' >> pop'>> let* x = top' in pop' >> let* y = top' in return (x + y)
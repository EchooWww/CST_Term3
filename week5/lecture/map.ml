module M = Map.Make(String)
let inc word map = 
  M.update word (function | None -> Some 1 | Some n -> Some (n+1)) map
let count() = 
  let rec aux map = 
    try
      (* scanf never throw an exception*)
      Scanf.scanf " %s" (fun x -> 
          if x = "" then raise End_of_file
          else aux (inc x map))

    with End_of_file -> map
  in M.bindings @@ aux M.empty
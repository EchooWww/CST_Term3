module type D = sig 
  type t                              
  type edge = string * string * int   
  exception Inv_edge
  exception Inv_graph
  val empty : t
  val add_edge : edge -> t -> t  
  val of_edges : edge list -> t 
  val edges : t -> edge list  
  val vertices : t -> string list 
  val neighbors : string -> t -> (string * int) list 
end

module StringMap = Map.Make(String)

module Digraph = struct 
  type t = (string * int) list StringMap.t
  type edge = string * string * int
  exception Inv_edge
  exception Inv_graph
  let empty = StringMap.empty

  let is_valid_edge = function
    | (v1,v2,distance) when v1<>v2 && distance >0 && v1 <> "" && v2 <> "" ->true
    | _ -> false


  let rec add_edge edge t = 
    if not (is_valid_edge edge) then raise Inv_edge
    else 
      let (x, y, z) = edge in
      match StringMap.find_opt x t with
      | Some neighbors ->
        let rec find_dup_and_add neighbors = 
          match neighbors with
          | (y1, z1)::rest ->
            if y = y1 then
              if z = z1 then t
              else raise Inv_graph
            else find_dup_and_add rest
          | [] -> StringMap.add_to_list x (y,z) t
        in
        find_dup_and_add neighbors
      | None ->
        StringMap.add x [(y,z)] t

  let rec of_edges = function
    | [] -> empty
    | e::es -> add_edge e (of_edges es)

  let rec map_to_list map acc = 
    let kv_list = StringMap.bindings map in
    match kv_list with
    | (x, (y1,y2)::ys)::zs -> map_to_list (StringMap.add x ys (StringMap.of_seq (List.to_seq zs))) ((x, y1, y2)::acc)
    | (x, [])::zs -> map_to_list (StringMap.of_seq (List.to_seq zs)) acc
    | [] -> acc

  let edges g =
    let edge_list = map_to_list g [] in
    List.sort (fun (x1, y1, z1) (x2, y2, z2) ->
        if x1 <> x2 then compare x1 x2
        else if y1 <> y2 then compare y1 y2
        else compare z1 z2
      ) edge_list

  let vertices g = 
    StringMap.bindings g 
    |> List.map (fun (x, lst)->(x::List.map fst lst)) 
    |> List.flatten 
    |> List.sort_uniq compare

  let neighbors v g = 
    match StringMap.find_opt v g with
    | Some neighbors -> neighbors
    | None -> []
end
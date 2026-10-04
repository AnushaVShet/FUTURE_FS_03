const API="https://futurefs03-production-a9fd.up.railway.app/api/products";let all=[];let category="All";
const imgs=["https://images.unsplash.com/photo-1583391733956-6c78276477e2?auto=format&fit=crop&w=900&q=85","https://images.unsplash.com/photo-1610030469983-98e550d1b7e7?auto=format&fit=crop&w=900&q=85","https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?auto=format&fit=crop&w=900&q=85","https://images.unsplash.com/photo-1539109136881-3be0616acf4b?auto=format&fit=crop&w=900&q=85"];
function money(n){return "₹"+Number(n).toLocaleString("en-IN",{maximumFractionDigits:0})}
function bag(){return JSON.parse(localStorage.getItem("anveraBag")||"[]")}
function update(){document.getElementById("bagCount").textContent=bag().reduce((s,x)=>s+x.quantity,0)}
function toast(m){const t=document.getElementById("toast");t.textContent=m;t.classList.add("show");setTimeout(()=>t.classList.remove("show"),2000)}
function add(p){let b=bag(),x=b.find(i=>i.id===p.id);if(x)x.quantity++;else b.push({...p,quantity:1});localStorage.setItem("anveraBag",JSON.stringify(b));update();toast("Added to bag")}
function render(){
 let q=new URLSearchParams(location.search).get("search")?.toLowerCase()||"";
 let data=all.filter(p=>(category==="All"||p.category===category)&&(!q||p.name.toLowerCase().includes(q)||p.category.toLowerCase().includes(q)));
 const s=document.getElementById("sort").value;if(s==="low")data.sort((a,b)=>a.price-b.price);if(s==="high")data.sort((a,b)=>b.price-a.price);if(s==="name")data.sort((a,b)=>a.name.localeCompare(b.name));
 document.getElementById("shopGrid").innerHTML=data.map((p,i)=>`<article class="product-card"><a href="product.html?id=${p.id}"><div class="product-image"><img src="${p.imageUrl||imgs[i%imgs.length]}" onerror="this.src='${imgs[i%imgs.length]}'" alt="${p.name}"></div></a><div class="product-info"><p class="product-name">${p.name}</p><div class="product-meta">${p.category} · ${p.stock} available</div><div class="price">${money(p.price)}</div><button class="add-btn" onclick='add(${JSON.stringify(p)})'>${p.stock?"ADD TO BAG":"SOLD OUT"}</button></div></article>`).join("")||"<p>No pieces found.</p>";
}
document.querySelectorAll(".category-bar button").forEach(b=>b.onclick=()=>{document.querySelector(".category-bar .active").classList.remove("active");b.classList.add("active");category=b.dataset.cat;render()});
document.getElementById("sort").onchange=render;
fetch(API).then(r=>r.json()).then(x=>{all=x;const c=new URLSearchParams(location.search).get("category");if(c){category=c;document.querySelectorAll(".category-bar button").forEach(b=>b.classList.toggle("active",b.dataset.cat===c))}render();update()}).catch(()=>document.getElementById("shopGrid").innerHTML="<p>Backend not connected. Start Spring Boot on port 8084.</p>");

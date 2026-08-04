import json, base64

logo_path = 'app/src/main/res/drawable/amatos_logo.png'
with open(logo_path, 'rb') as f:
    logo_b64 = base64.b64encode(f.read()).decode('utf-8')

sandwiches = [
    {"id": "sand_1", "name": "Simples", "category": "SANDUÍCHES", "description": "pão e hambúrguer bovino", "price": 8.0, "priceText": "R$ 8,00"},
    {"id": "sand_2", "name": "Burguer", "category": "SANDUÍCHES", "description": "pão, hambúrguer bovino e queijo", "price": 9.0, "priceText": "R$ 9,00"},
    {"id": "sand_3", "name": "Misto", "category": "SANDUÍCHES", "description": "pão, queijo e presunto", "price": 9.0, "priceText": "R$ 9,00"},
    {"id": "sand_4", "name": "Misto Burguer", "category": "SANDUÍCHES", "description": "pão, hambúrguer bovino, queijo e presunto", "price": 17.0, "priceText": "R$ 17,00"},
    {"id": "sand_5", "name": "Misto Calabresa", "category": "SANDUÍCHES", "description": "pão, queijo, presunto e calabresa", "price": 19.0, "priceText": "R$ 19,00"},
    {"id": "sand_6", "name": "Misto Bacon", "category": "SANDUÍCHES", "description": "pão, queijo, presunto e bacon", "price": 20.0, "priceText": "R$ 20,00"},
    {"id": "sand_7", "name": "Misto Frango", "category": "SANDUÍCHES", "description": "pão, queijo, presunto e frango", "price": 20.0, "priceText": "R$ 20,00"},
    {"id": "sand_8", "name": "Baurú", "category": "SANDUÍCHES", "description": "pão, queijo, tomate e orégano", "price": 8.0, "priceText": "R$ 8,00"},
    {"id": "sand_9", "name": "Americano 1", "category": "SANDUÍCHES", "description": "pão, queijo, presunto, ovo e salada", "price": 15.0, "priceText": "R$ 15,00"},
    {"id": "sand_10", "name": "Americano 2", "category": "SANDUÍCHES", "description": "pão, hambúrguer, queijo, bacon e salada", "price": 16.0, "priceText": "R$ 16,00"},
    {"id": "sand_11", "name": "Americano 3", "category": "SANDUÍCHES", "description": "pão, hambúrguer, queijo, bacon, salada e ovo", "price": 18.0, "priceText": "R$ 18,00"},
    {"id": "sand_12", "name": "X-Frango", "category": "SANDUÍCHES", "description": "pão, frango, queijo e salada", "price": 21.0, "priceText": "R$ 21,00"},
    {"id": "sand_13", "name": "X-Frango Tudo", "category": "SANDUÍCHES", "description": "pão, frango, queijo, ovo, bacon, calabresa e salada", "price": 25.0, "priceText": "R$ 25,00", "isPopular": True},
    {"id": "sand_14", "name": "Amatos Frango", "category": "SANDUÍCHES", "description": "pão, filé de frango, queijo coalho e salada", "price": 26.0, "priceText": "R$ 26,00"},
    {"id": "sand_15", "name": "Amatos Alcatra", "category": "SANDUÍCHES", "description": "pão, filé de alcatra, queijo coalho e salada", "price": 28.0, "priceText": "R$ 28,00", "isPopular": True},
    {"id": "sand_16", "name": "Amatos Bacon", "category": "SANDUÍCHES", "description": "pão, hambúrguer bovino, bacon, cheddar e salada", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "sand_17", "name": "X-Bacon", "category": "SANDUÍCHES", "description": "pão, bacon, queijo e salada", "price": 22.0, "priceText": "R$ 22,00"},
    {"id": "sand_18", "name": "X-Bacon Egg", "category": "SANDUÍCHES", "description": "pão, bacon, queijo, ovo e salada", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "sand_19", "name": "X-Bacon Frango", "category": "SANDUÍCHES", "description": "pão, bacon, queijo, frango e salada", "price": 25.0, "priceText": "R$ 25,00"},
    {"id": "sand_20", "name": "X-Calabresa", "category": "SANDUÍCHES", "description": "pão, hambúrguer, queijo, calabresa e salada", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "sand_21", "name": "Mistão", "category": "SANDUÍCHES", "description": "pão, filé de alcatra, filé de frango, calabresa, queijo, presunto e salada", "price": 33.0, "priceText": "R$ 33,00", "isPopular": True},
    {"id": "sand_22", "name": "X-3 Queijos", "category": "SANDUÍCHES", "description": "pão, hambúrguer bovino, mussarela, catupiry e cheddar", "price": 22.0, "priceText": "R$ 22,00"},
    {"id": "sand_23", "name": "X-Egg", "category": "SANDUÍCHES", "description": "pão, hambúrguer bovino, queijo e ovo", "price": 18.0, "priceText": "R$ 18,00"},
    {"id": "sand_24", "name": "X-Salada", "category": "SANDUÍCHES", "description": "pão, hambúrguer bovino, queijo e salada", "price": 19.0, "priceText": "R$ 19,00"},
    {"id": "sand_25", "name": "X-Salada Egg", "category": "SANDUÍCHES", "description": "pão, hambúrguer bovino, queijo, salada e ovo", "price": 20.0, "priceText": "R$ 20,00"},
    {"id": "sand_26", "name": "X-Omelete", "category": "SANDUÍCHES", "description": "pão, queijo, presunto, cebola e ovo", "price": 20.0, "priceText": "R$ 20,00"},
    {"id": "sand_27", "name": "X-Tudo 1", "category": "SANDUÍCHES", "description": "pão, hambúrguer, queijo, presunto, ovo, bacon, calabresa e salada", "price": 21.0, "priceText": "R$ 21,00"},
    {"id": "sand_28", "name": "X-Tudo 2", "category": "SANDUÍCHES", "description": "pão, 2 hambúrgueres, queijo, presunto, ovo, bacon, calabresa e salada", "price": 22.0, "priceText": "R$ 22,00"},
    {"id": "sand_29", "name": "X-Tudo 3", "category": "SANDUÍCHES", "description": "pão, 3 hambúrgueres, queijo, presunto, ovo, bacon, calabresa e salada", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "sand_30", "name": "X-Tudo 4", "category": "SANDUÍCHES", "description": "pão, 4 hambúrgueres, queijo, presunto, ovo, bacon, calabresa e salada", "price": 24.0, "priceText": "R$ 24,00", "isPopular": True},
    {"id": "sand_31", "name": "X-Frango Calabresa", "category": "SANDUÍCHES", "description": "pão, frango, calabresa, queijo e salada", "price": 21.0, "priceText": "R$ 21,00"},
    {"id": "sand_32", "name": "X-Frango Bacon", "category": "SANDUÍCHES", "description": "pão, frango, bacon, queijo e salada", "price": 22.0, "priceText": "R$ 22,00"},
    {"id": "sand_33", "name": "X-Gostoso Frango", "category": "SANDUÍCHES", "description": "pão, frango, hambúrguer bovino, queijo e tomate", "price": 22.0, "priceText": "R$ 22,00"},
    {"id": "sand_34", "name": "X-Churrasco 1", "category": "SANDUÍCHES", "description": "pão francês, filé bovino, queijo e cebola", "price": 25.0, "priceText": "R$ 25,00"},
    {"id": "sand_35", "name": "X-Churrasco 2", "category": "SANDUÍCHES", "description": "pão francês, filé bovino, queijo, bacon, ovo, cebola e salada", "price": 32.0, "priceText": "R$ 32,00"},
    {"id": "sand_36", "name": "X-Light", "category": "SANDUÍCHES", "description": "pão, frango, queijo e salada", "price": 21.0, "priceText": "R$ 21,00"},
    {"id": "sand_37", "name": "X-Gordo", "category": "SANDUÍCHES", "description": "pão, calabresa, 2 hambúrgueres, ovo, cebola, tomate e orégano", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "sand_38", "name": "X-Especial", "category": "SANDUÍCHES", "description": "pão, frango, hambúrguer, queijo, presunto, ovo, bacon e calabresa", "price": 26.0, "priceText": "R$ 26,00"},
    {"id": "sand_39", "name": "X-Frango com Cheddar", "category": "SANDUÍCHES", "description": "pão, frango, queijo, cheddar e salada", "price": 23.0, "priceText": "R$ 23,00"}
]

pastels = [
    {"id": "past_1", "name": "Barroso", "category": "PASTÉIS", "description": "frango com queijo", "price": 22.0, "priceText": "R$ 22,00"},
    {"id": "past_2", "name": "Palmeiras", "category": "PASTÉIS", "description": "frango com presunto", "price": 21.0, "priceText": "R$ 21,00"},
    {"id": "past_3", "name": "Bulevar", "category": "PASTÉIS", "description": "frango com cheddar", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "past_4", "name": "Itaperi", "category": "PASTÉIS", "description": "frango com catupiry", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "past_5", "name": "Mondubim", "category": "PASTÉIS", "description": "frango com bacon", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "past_6", "name": "Itaitinga", "category": "PASTÉIS", "description": "frango, queijo e presunto", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "past_7", "name": "Estrada do Fio", "category": "PASTÉIS", "description": "carne com queijo", "price": 22.0, "priceText": "R$ 22,00"},
    {"id": "past_8", "name": "Messejana", "category": "PASTÉIS", "description": "carne, queijo e ovo", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "past_9", "name": "Parque Iracema", "category": "PASTÉIS", "description": "carne moída", "price": 20.0, "priceText": "R$ 20,00"},
    {"id": "past_10", "name": "Pedras", "category": "PASTÉIS", "description": "carne do sol com queijo", "price": 30.0, "priceText": "R$ 30,00", "isPopular": True},
    {"id": "past_11", "name": "Caucaia", "category": "PASTÉIS", "description": "queijo, presunto e ovo", "price": 21.0, "priceText": "R$ 21,00"},
    {"id": "past_12", "name": "Av. Perimental", "category": "PASTÉIS", "description": "presunto com milho", "price": 21.0, "priceText": "R$ 21,00"},
    {"id": "past_13", "name": "Conj. Palmeiras", "category": "PASTÉIS", "description": "presunto e queijo", "price": 19.0, "priceText": "R$ 19,00"},
    {"id": "past_14", "name": "Santa Filomena", "category": "PASTÉIS", "description": "atum com palmito", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "past_15", "name": "São Cristóvão", "category": "PASTÉIS", "description": "presunto e queijo", "price": 19.0, "priceText": "R$ 19,00"},
    {"id": "past_16", "name": "Pq. 2 irmãos", "category": "PASTÉIS", "description": "carne do sol, banana da terra e queijo", "price": 35.0, "priceText": "R$ 35,00", "isPopular": True},
    {"id": "past_17", "name": "Luiz Gonzaga", "category": "PASTÉIS", "description": "atum com queijo", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "past_18", "name": "Parquelândia", "category": "PASTÉIS", "description": "calabresa com queijo", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "past_19", "name": "Bairro Novo", "category": "PASTÉIS", "description": "calabresa, carne, queijo, presunto e frango", "price": 25.0, "priceText": "R$ 25,00"},
    {"id": "past_20", "name": "Ancuri", "category": "PASTÉIS", "description": "filé de alcatra ou maminha com queijo", "price": 27.0, "priceText": "R$ 27,00"},
    {"id": "past_21", "name": "Jangurussú", "category": "PASTÉIS", "description": "bacon com queijo", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "past_22", "name": "Vl. Manoel Sátiro", "category": "PASTÉIS", "description": "bacon, carne e queijo", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "past_23", "name": "Sabiaguaba", "category": "PASTÉIS", "description": "camarão com queijo", "price": 30.0, "priceText": "R$ 30,00"},
    {"id": "past_24", "name": "Parangaba", "category": "PASTÉIS", "description": "camarão com cheddar", "price": 30.0, "priceText": "R$ 30,00"},
    {"id": "past_25", "name": "Maraponga", "category": "PASTÉIS", "description": "camarão com cream cheese", "price": 32.0, "priceText": "R$ 32,00", "isPopular": True},
    {"id": "past_26", "name": "Granja Lisboa", "category": "PASTÉIS", "description": "camarão com catupiry", "price": 31.0, "priceText": "R$ 31,00"},
    {"id": "past_27", "name": "Praia do Futuro", "category": "PASTÉIS", "description": "lombinho com queijo e milho", "price": 25.0, "priceText": "R$ 25,00"},
    {"id": "past_28", "name": "Violeta", "category": "PASTÉIS", "description": "lombinho com cream cheese", "price": 26.0, "priceText": "R$ 26,00"},
    {"id": "past_29", "name": "Zé Walter", "category": "PASTÉIS", "description": "queijo", "price": 35.0, "priceText": "R$ 35,00"},
    {"id": "past_30", "name": "Lagoa Redonda", "category": "PASTÉIS", "description": "queijo com goiabada", "price": 20.0, "priceText": "R$ 20,00"},
    {"id": "past_31", "name": "Curió", "category": "PASTÉIS", "description": "presunto, queijo, ovo, azeitona e cebola", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "past_32", "name": "Damas", "category": "PASTÉIS", "description": "frango, passas, milho e ervilha", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "past_33", "name": "Varjota", "category": "PASTÉIS", "description": "carne, passas, pim. malagueta ou pim. calabresa", "price": 23.0, "priceText": "R$ 23,00"},
    {"id": "past_34", "name": "Meireles", "category": "PASTÉIS", "description": "brocólis, palmito, tomate, cereja e peito de peru", "price": 25.0, "priceText": "R$ 25,00"},
    {"id": "past_35", "name": "B. do Ceará", "category": "PASTÉIS", "description": "carne e frango", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "past_36", "name": "Passare (Pastel Japonês)", "category": "PASTÉIS", "description": "camarão, kane kamma e cream cheese", "price": 35.0, "priceText": "R$ 35,00"},
    {"id": "past_37", "name": "Vila Velha", "category": "PASTÉIS", "description": "ovo, queijo e presunto", "price": 21.0, "priceText": "R$ 21,00"},
    {"id": "past_38", "name": "Pirambu", "category": "PASTÉIS", "description": "frango, milho e ervilha", "price": 22.0, "priceText": "R$ 22,00"},
    {"id": "past_39", "name": "Aldeota", "category": "PASTÉIS", "description": "carne do sol, queijo e purê de aipim", "price": 35.0, "priceText": "R$ 35,00", "isPopular": True},
    {"id": "past_40", "name": "Castelo Encantado", "category": "PASTÉIS", "description": "frango com requeijão cremoso", "price": 24.0, "priceText": "R$ 24,00"},
    {"id": "past_41", "name": "Caça e Pesca", "category": "PASTÉIS", "description": "carne do sol, macaxeira frita e queijo coalho", "price": 30.0, "priceText": "R$ 30,00"}
]

snacks = [
    {"id": "pet_1", "name": "Caixa da Felicidade", "category": "PETISCOS", "description": "(Batata Frita, Contra Filé, Toscana, Asinha, Coxinha da Asa, Arroz à Grega, Salada de Maionese, Baião Cremoso, Farofa e Vinagrete)", "price": 105.0, "priceText": "R$ 105,00", "isPopular": True},
    {"id": "pet_2", "name": "Baião Cremoso", "category": "PETISCOS", "description": "(acompanha: Carne do sol)", "price": 60.0, "priceText": "R$ 60,00"},
    {"id": "pet_3", "name": "Pirão de Aipim (Camarão)", "category": "PETISCOS", "description": "(acompanha: Macaxeira, Camarão, Queijo Ralado e Vinagrete)", "price": 70.0, "priceText": "R$ 70,00"},
    {"id": "pet_4", "name": "Pirão de Aipim (Carne do Sol)", "category": "PETISCOS", "description": "(acompanha: Macaxeira, Carne do Sol, Queijo Ralado e Vinagrete)", "price": 60.0, "priceText": "R$ 60,00"},
    {"id": "pet_5", "name": "Escondidinho (Camarão)", "category": "PETISCOS", "description": "(acompanha: Macaxeira, Camarão e Mussarela Ralada)", "price": 80.0, "priceText": "R$ 80,00"},
    {"id": "pet_6", "name": "Escondidinho (Carne do Sol)", "category": "PETISCOS", "description": "(acompanha: Macaxeira, Carne do Sol e Mussarela Ralada)", "price": 70.0, "priceText": "R$ 70,00"},
    {"id": "pet_7", "name": "Lasanha", "category": "PETISCOS", "description": "(opções: Carne ou Frango ou Queijo e Presunto)", "price": 40.0, "priceText": "R$ 40,00", "options": ["Carne", "Frango", "Queijo e Presunto"]},
    {"id": "pet_8", "name": "Comida Baiana", "category": "PETISCOS", "description": "(acompanha: Arroz, Caruru, Feijão Fradinho, Vatapá e Farofa - opções de proteína: Peixe ou Frango)", "price": None, "priceText": "Consulte preços", "options": ["Peixe", "Frango"]},
    {"id": "pet_9", "name": "Bobó de Camarão", "category": "PETISCOS", "description": "(porção única)", "price": 75.0, "priceText": "R$ 75,00"},
    {"id": "pet_10", "name": "Batata Frita Simples", "category": "PETISCOS", "description": "(porção tradicional)", "price": 30.0, "priceText": "R$ 30,00"},
    {"id": "pet_11", "name": "Batata Frita com Calabresa", "category": "PETISCOS", "description": "(acompanha calabresa fatiada)", "price": 33.0, "priceText": "R$ 33,00"},
    {"id": "pet_12", "name": "Batata Frita com Bacon e Cheddar", "category": "PETISCOS", "description": "(coberta com molho cheddar e cubos de bacon)", "price": 35.0, "priceText": "R$ 35,00", "isPopular": True}
]

all_items = sandwiches + pastels + snacks
items_json = json.dumps(all_items, ensure_ascii=False)

html_template = """<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Amatos Lanches - Cardápio Online</title>
    <meta name="description" content="Peça os melhores sanduíches, pastéis e petiscos do Amatos Lanches via WhatsApp!">
    <link rel="icon" type="image/png" href="data:image/png;base64,%%LOGO_B64%%">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Plus+Jakarta+Sans:wght@400;600;700;800&display=swap" rel="stylesheet">
    <style>
        :root {
            --red-primary: #E53935;
            --red-dark: #C62828;
            --yellow-banner: #FBC02D;
            --yellow-text: #3E2723;
            --bg-body: #F5F3EF;
            --card-bg: #FFFFFF;
            --text-dark: #212121;
            --text-muted: #666666;
            --whatsapp-green: #25D366;
            --font-family: 'Plus Jakarta Sans', system-ui, -apple-system, sans-serif;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: var(--font-family);
            -webkit-tap-highlight-color: transparent;
        }

        body {
            background-color: var(--bg-body);
            color: var(--text-dark);
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }

        /* App Layout Container (Mobile Frame on Desktop) */
        .app-container {
            width: 100%;
            max-width: 500px;
            background: #FFFFFF;
            min-height: 100vh;
            box-shadow: 0 0 25px rgba(0,0,0,0.1);
            position: relative;
            display: flex;
            flex-direction: column;
            padding-bottom: 90px;
        }

        /* Header */
        header {
            background: linear-gradient(180deg, var(--red-dark) 0%, var(--red-primary) 100%);
            color: white;
            padding: 16px 16px 0;
            text-align: center;
            position: sticky;
            top: 0;
            z-index: 100;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        .brand-title {
            font-size: 20px;
            font-weight: 800;
            letter-spacing: 0.5px;
            margin-bottom: 8px;
            text-transform: uppercase;
        }

        .logo-img {
            height: 110px;
            max-width: 100%;
            object-fit: contain;
            margin: 0 auto 6px;
            display: block;
        }

        /* Delivery Phone Banner */
        .delivery-banner {
            background-color: var(--yellow-banner);
            color: var(--yellow-text);
            font-weight: 700;
            font-size: 14px;
            padding: 8px 12px;
            border-radius: 8px;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 6px;
            cursor: pointer;
            box-shadow: 0 2px 5px rgba(0,0,0,0.15);
            text-decoration: none;
            width: calc(100% - 10px);
            margin: 4px auto 8px;
        }

        /* Search Input */
        .search-box {
            padding: 0 4px;
            margin-top: 6px;
            margin-bottom: 10px;
        }

        .search-input {
            width: 100%;
            padding: 10px 14px;
            border-radius: 20px;
            border: 1px solid rgba(255,255,255,0.4);
            background: rgba(255,255,255,0.95);
            font-size: 14px;
            outline: none;
            color: #333;
        }

        /* Category Nav Tabs */
        .nav-tabs {
            display: flex;
            background: #FFFFFF;
            overflow-x: auto;
            border-bottom: 1px solid #EEEEEE;
            margin-left: -16px;
            margin-right: -16px;
            scrollbar-width: none;
        }
        .nav-tabs::-webkit-scrollbar { display: none; }

        .tab-btn {
            flex: 0 0 auto;
            padding: 12px 16px;
            font-size: 13px;
            font-weight: 700;
            color: var(--text-muted);
            border: none;
            background: none;
            cursor: pointer;
            border-bottom: 3px solid transparent;
            text-transform: uppercase;
            white-space: nowrap;
        }

        .tab-btn.active {
            color: var(--red-primary);
            border-bottom-color: var(--red-primary);
        }

        /* Menu List */
        .menu-list {
            padding: 12px 14px;
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .card-item {
            background: #FFFFFF;
            border: 1px solid #EEEEEE;
            border-radius: 12px;
            padding: 12px 14px;
            display: flex;
            flex-direction: column;
            gap: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.03);
            position: relative;
            transition: transform 0.1s ease;
        }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            gap: 8px;
        }

        .item-title {
            font-size: 16px;
            font-weight: 700;
            color: var(--text-dark);
        }

        .badge-popular {
            background-color: #FFF3E0;
            color: #E65100;
            font-size: 10px;
            font-weight: 800;
            padding: 2px 8px;
            border-radius: 10px;
            text-transform: uppercase;
            margin-left: 6px;
        }

        .item-desc {
            font-size: 13px;
            color: var(--text-muted);
            line-height: 1.35;
        }

        .card-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 4px;
        }

        .item-price {
            font-size: 15px;
            font-weight: 800;
            color: var(--red-dark);
        }

        .card-actions {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .fav-btn {
            background: none;
            border: none;
            font-size: 18px;
            cursor: pointer;
            color: #CCCCCC;
            padding: 4px;
        }
        .fav-btn.active {
            color: #E53935;
        }

        .add-btn {
            background: var(--red-primary);
            color: white;
            border: none;
            padding: 7px 14px;
            border-radius: 16px;
            font-weight: 700;
            font-size: 12px;
            cursor: pointer;
        }

        .qty-control {
            display: flex;
            align-items: center;
            background: #F0F0F0;
            border-radius: 16px;
            overflow: hidden;
        }

        .qty-btn {
            border: none;
            background: transparent;
            padding: 6px 10px;
            font-weight: 800;
            cursor: pointer;
            color: var(--red-primary);
        }

        .qty-num {
            font-size: 13px;
            font-weight: 700;
            padding: 0 4px;
        }

        /* Floating Cart Bar */
        .cart-bar {
            position: fixed;
            bottom: 16px;
            left: 50%;
            transform: translateX(-50%);
            width: calc(100% - 32px);
            max-width: 468px;
            background: var(--red-primary);
            color: white;
            border-radius: 30px;
            padding: 12px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-shadow: 0 6px 20px rgba(229, 57, 53, 0.4);
            cursor: pointer;
            z-index: 100;
            animation: pulseCart 2s infinite alternate;
        }

        @keyframes pulseCart {
            0% { transform: translateX(-50%) scale(1); }
            100% { transform: translateX(-50%) scale(1.02); }
        }

        .cart-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .cart-badge {
            background: white;
            color: var(--red-primary);
            font-weight: 800;
            border-radius: 12px;
            padding: 2px 8px;
            font-size: 12px;
        }

        /* Modal Overlay */
        .modal-overlay {
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background: rgba(0,0,0,0.5);
            display: none;
            justify-content: center;
            align-items: flex-end;
            z-index: 1000;
        }
        .modal-overlay.active {
            display: flex;
        }

        .modal-content {
            background: white;
            width: 100%;
            max-width: 500px;
            border-top-left-radius: 20px;
            border-top-right-radius: 20px;
            padding: 20px;
            max-height: 85vh;
            overflow-y: auto;
            position: relative;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 16px;
            border-bottom: 1px solid #EEE;
            padding-bottom: 10px;
        }

        .close-btn {
            background: none;
            border: none;
            font-size: 24px;
            font-weight: 700;
            cursor: pointer;
            color: #666;
        }

        .form-group {
            margin-bottom: 12px;
        }

        .form-label {
            font-size: 13px;
            font-weight: 700;
            color: #444;
            margin-bottom: 4px;
            display: block;
        }

        .form-input, .form-select {
            width: 100%;
            padding: 10px 12px;
            border: 1px solid #CCC;
            border-radius: 8px;
            font-size: 14px;
            outline: none;
        }
        .form-input:focus, .form-select:focus {
            border-color: var(--red-primary);
        }

        .btn-whatsapp {
            width: 100%;
            background: var(--whatsapp-green);
            color: white;
            border: none;
            padding: 14px;
            border-radius: 12px;
            font-weight: 800;
            font-size: 15px;
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 8px;
            margin-top: 16px;
            box-shadow: 0 4px 12px rgba(37, 211, 102, 0.3);
        }

        /* Empty state */
        .empty-state {
            text-align: center;
            padding: 40px 20px;
            color: var(--text-muted);
        }
    </style>
</head>
<body>

<div class="app-container">
    <header>
        <div style="text-align: center; margin-bottom: 8px;">
            <img src="https://i.ibb.co/4Zt4bZq5/MIOLO-LOGOMARCA.png" alt="AMATOS LANCHES" style="max-height: 80px; width: auto; object-fit: contain;">
        </div>
        <div class="brand-title">AMATOS LANCHES</div>
        <a href="tel:85986050960" class="delivery-banner">
            <span>📞 DELIVERY: 85 98605-0960</span>
        </a>

        <div class="search-box">
            <input type="text" id="searchInput" class="search-input" placeholder="🔍 Pesquisar lanches, pastéis..." oninput="filterMenu()">
        </div>

        <div class="nav-tabs">
            <button class="tab-btn active" onclick="switchCategory('SANDUÍCHES', this)">SANDUÍCHES</button>
            <button class="tab-btn" onclick="switchCategory('PASTÉIS', this)">PASTÉIS</button>
            <button class="tab-btn" onclick="switchCategory('PETISCOS', this)">PETISCOS</button>
        </div>
    </header>

    <main class="menu-list" id="menuList">
        <!-- Rendered dynamically -->
    </main>

    <!-- Floating Cart Bar -->
    <div class="cart-bar" id="cartBar" style="display: none;" onclick="openCartModal()">
        <div class="cart-info">
            <span class="cart-badge" id="cartBadgeCount">0</span>
            <span style="font-weight: 700;">Ver Sacola</span>
        </div>
        <span style="font-weight: 800; font-size: 16px;" id="cartBarTotal">R$ 0,00</span>
    </div>
</div>

<!-- Item Options Modal -->
<div class="modal-overlay" id="itemModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 id="modalItemTitle" style="font-size: 18px; font-weight: 800;">Detalhes do Item</h3>
            <button class="close-btn" onclick="closeModal('itemModal')">&times;</button>
        </div>
        <p id="modalItemDesc" style="color: #666; font-size: 13px; margin-bottom: 12px;"></p>
        
        <div class="form-group" id="optionGroup" style="display: none;">
            <label class="form-label">Escolha a Opção / Sabor:</label>
            <select id="modalOptionSelect" class="form-select"></select>
        </div>

        <div class="form-group">
            <label class="form-label">Observações (opcional):</label>
            <input type="text" id="modalObservation" class="form-input" placeholder="Ex: Sem cebola, molho à parte...">
        </div>

        <button class="add-btn" style="width: 100%; padding: 12px; font-size: 14px; margin-top: 10px;" onclick="confirmAddItem()">ADICIONAR À SACOLA</button>
    </div>
</div>

<!-- Cart Modal -->
<div class="modal-overlay" id="cartModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3 style="font-size: 18px; font-weight: 800;">Sua Sacola</h3>
            <button class="close-btn" onclick="closeModal('cartModal')">&times;</button>
        </div>

        <div id="cartItemsContainer"></div>

        <div style="border-top: 2px dashed #EEE; margin: 16px 0; padding-top: 12px;">
            <div class="form-group">
                <label class="form-label">Seu Nome:</label>
                <input type="text" id="custName" class="form-input" placeholder="Digite seu nome">
            </div>

            <div class="form-group">
                <label class="form-label">Endereço para Entrega:</label>
                <input type="text" id="custAddress" class="form-input" placeholder="Rua, número e bairro (ou Retirada no balcão)">
            </div>

            <div class="form-group">
                <label class="form-label">Ponto de Referência:</label>
                <input type="text" id="custReference" class="form-input" placeholder="Ex: Próximo à praça, casa amarela...">
            </div>

            <div class="form-group">
                <label class="form-label">Observações do Pedido (opcional):</label>
                <input type="text" id="custOrderObs" class="form-input" placeholder="Ex: bem passado, sem cebola, etc...">
            </div>

            <div class="form-group">
                <label class="form-label">Forma de Pagamento:</label>
                <select id="custPayment" class="form-select" onchange="toggleTroco(this.value)">
                    <option value="Pix">Pix</option>
                    <option value="Cartão de Crédito/Débito">Cartão de Crédito/Débito</option>
                    <option value="Dinheiro">Dinheiro</option>
                </select>
            </div>

            <div class="form-group" id="trocoGroup" style="display: none;">
                <label class="form-label">Troco para quanto?</label>
                <input type="text" id="custTroco" class="form-input" placeholder="Ex: R$ 50,00">
            </div>

            <div style="display: flex; justify-content: space-between; font-weight: 800; font-size: 17px; margin-top: 16px;">
                <span>Total:</span>
                <span id="cartModalTotal" style="color: var(--red-dark);">R$ 0,00</span>
            </div>
        </div>

        <button class="btn-whatsapp" onclick="sendWhatsAppOrder()">
            <span>💬 ENVIAR PEDIDO NO WHATSAPP</span>
        </button>
    </div>
</div>

<script>
    const menuData = %%ITEMS_JSON%%;
    let currentCategory = 'SANDUÍCHES';
    let cart = JSON.parse(localStorage.getItem('amatos_cart') || '[]');
    let activeModalItem = null;

    document.addEventListener('DOMContentLoaded', () => {
        document.getElementById('custName').value = localStorage.getItem('amatos_name') || '';
        document.getElementById('custAddress').value = localStorage.getItem('amatos_address') || '';
        document.getElementById('custReference').value = localStorage.getItem('amatos_ref') || '';
        renderMenu();
        updateCartBar();
    });

    function switchCategory(cat, btn) {
        currentCategory = cat;
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        if (btn) btn.classList.add('active');
        renderMenu();
    }

    function filterMenu() {
        renderMenu();
    }

    function renderMenu() {
        const query = document.getElementById('searchInput').value.toLowerCase().trim();
        const list = document.getElementById('menuList');
        list.innerHTML = '';

        let items = [];
        if (query) {
            items = menuData.filter(i => i.name.toLowerCase().includes(query) || i.description.toLowerCase().includes(query));
        } else {
            items = menuData.filter(i => i.category === currentCategory);
        }

        if (items.length === 0) {
            list.innerHTML = `<div class="empty-state">Nenhum item encontrado.</div>`;
            return;
        }

        items.forEach(item => {
            const inCart = cart.find(c => c.id === item.id);

            const card = document.createElement('div');
            card.className = 'card-item';
            card.innerHTML = `
                <div class="card-header">
                    <div>
                        <span class="item-title">${item.name}</span>
                        ${query ? `<span style="font-size: 11px; color: #888; font-weight: 600; margin-left: 6px;">(${item.category})</span>` : ''}
                    </div>
                </div>
                <div class="item-desc">${item.description}</div>
                <div class="card-footer">
                    <span class="item-price">${item.priceText}</span>
                    <div class="card-actions">
                        ${inCart ? `
                            <div class="qty-control">
                                <button class="qty-btn" onclick="changeQty('${item.id}', -1)">-</button>
                                <span class="qty-num">${inCart.quantity}</span>
                                <button class="qty-btn" onclick="changeQty('${item.id}', 1)">+</button>
                            </div>
                        ` : `
                            <button class="add-btn" onclick="openItemModal('${item.id}')">+ Adicionar</button>
                        `}
                    </div>
                </div>
            `;
            list.appendChild(card);
        });
    }

    function toggleFavorite(id, e) {
        e.stopPropagation();
        if (favorites.includes(id)) {
            favorites = favorites.filter(f => f !== id);
        } else {
            favorites.push(id);
        }
        localStorage.setItem('amatos_favs', JSON.stringify(favorites));
        renderMenu();
    }

    function openItemModal(id) {
        const item = menuData.find(i => i.id === id);
        if (!item) return;

        if (!item.options || item.options.length === 0) {
            addToCart(item, '', '');
            return;
        }

        activeModalItem = item;
        document.getElementById('modalItemTitle').innerText = item.name;
        document.getElementById('modalItemDesc').innerText = item.description;

        const optGroup = document.getElementById('optionGroup');
        const optSelect = document.getElementById('modalOptionSelect');
        optSelect.innerHTML = '';
        item.options.forEach(opt => {
            const o = document.createElement('option');
            o.value = opt;
            o.innerText = opt;
            optSelect.appendChild(o);
        });
        optGroup.style.display = 'block';

        document.getElementById('modalObservation').value = '';
        document.getElementById('itemModal').classList.add('active');
    }

    function confirmAddItem() {
        if (!activeModalItem) return;
        const optSelect = document.getElementById('modalOptionSelect');
        const selOpt = optSelect.value || '';
        const obs = document.getElementById('modalObservation').value.trim();
        addToCart(activeModalItem, selOpt, obs);
        closeModal('itemModal');
    }

    function addToCart(item, option = '', obs = '') {
        const existing = cart.find(c => c.id === item.id && c.selectedOption === option);
        if (existing) {
            existing.quantity += 1;
        } else {
            cart.push({
                id: item.id,
                name: item.name,
                price: item.price || 0,
                priceText: item.priceText,
                selectedOption: option,
                observation: obs,
                quantity: 1
            });
        }
        saveCart();
    }

    function changeQty(id, delta) {
        const idx = cart.findIndex(c => c.id === id);
        if (idx >= 0) {
            cart[idx].quantity += delta;
            if (cart[idx].quantity <= 0) {
                cart.splice(idx, 1);
            }
            saveCart();
        }
    }

    function saveCart() {
        localStorage.setItem('amatos_cart', JSON.stringify(cart));
        updateCartBar();
        renderMenu();
    }

    function updateCartBar() {
        const totalCount = cart.reduce((acc, c) => acc + c.quantity, 0);
        const totalPrice = cart.reduce((acc, c) => acc + (c.price * c.quantity), 0);
        const cartBar = document.getElementById('cartBar');

        if (totalCount > 0) {
            cartBar.style.display = 'flex';
            document.getElementById('cartBadgeCount').innerText = totalCount;
            document.getElementById('cartBarTotal').innerText = `R$ ${totalPrice.toFixed(2).replace('.', ',')}`;
        } else {
            cartBar.style.display = 'none';
        }
    }

    function openCartModal() {
        const container = document.getElementById('cartItemsContainer');
        container.innerHTML = '';

        if (cart.length === 0) {
            container.innerHTML = '<div class="empty-state">Sua sacola está vazia.</div>';
        } else {
            cart.forEach(item => {
                const div = document.createElement('div');
                div.style.cssText = 'display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; padding-bottom: 8px; border-bottom: 1px solid #F0F0F0;';
                div.innerHTML = `
                    <div>
                        <div style="font-weight: 700;">${item.name} ${item.selectedOption ? `(${item.selectedOption})` : ''}</div>
                        <div style="font-size: 12px; color: #666;">${item.priceText}</div>
                        ${item.observation ? `<div style="font-size: 11px; color: var(--red-primary);">Obs: ${item.observation}</div>` : ''}
                    </div>
                    <div class="qty-control">
                        <button class="qty-btn" onclick="changeQtyCart('${item.id}', '${item.selectedOption}', -1)">-</button>
                        <span class="qty-num">${item.quantity}</span>
                        <button class="qty-btn" onclick="changeQtyCart('${item.id}', '${item.selectedOption}', 1)">+</button>
                    </div>
                `;
                container.appendChild(div);
            });
        }

        const totalPrice = cart.reduce((acc, c) => acc + (c.price * c.quantity), 0);
        document.getElementById('cartModalTotal').innerText = `R$ ${totalPrice.toFixed(2).replace('.', ',')}`;
        document.getElementById('cartModal').classList.add('active');
    }

    function changeQtyCart(id, option, delta) {
        const item = cart.find(c => c.id === id && c.selectedOption === option);
        if (item) {
            item.quantity += delta;
            if (item.quantity <= 0) {
                cart = cart.filter(c => !(c.id === id && c.selectedOption === option));
            }
            saveCart();
            openCartModal();
        }
    }

    function toggleTroco(val) {
        document.getElementById('trocoGroup').style.display = (val === 'Dinheiro') ? 'block' : 'none';
    }

    function closeModal(id) {
        document.getElementById(id).classList.remove('active');
    }

    function sendWhatsAppOrder() {
        if (cart.length === 0) {
            alert('Adicione itens à sua sacola antes de enviar.');
            return;
        }

        const name = document.getElementById('custName').value.trim() || 'Cliente';
        const address = document.getElementById('custAddress').value.trim() || 'Retirada no Balcão';
        const reference = document.getElementById('custReference').value.trim();
        const orderObs = document.getElementById('custOrderObs').value.trim();
        let payment = document.getElementById('custPayment').value;
        const troco = document.getElementById('custTroco').value.trim();

        if (payment === 'Dinheiro' && troco) {
            payment += ` (Troco para ${troco})`;
        }

        localStorage.setItem('amatos_name', name);
        localStorage.setItem('amatos_address', address);
        localStorage.setItem('amatos_ref', reference);

        let msg = `*NOVO PEDIDO - AMATOS LANCHES*\n`;
        msg += `----------------------------------\n`;
        msg += `*Cliente:* ${name}\n`;
        msg += `*Endereço para Entrega:* ${address}\n`;
        if (reference) {
            msg += `*Ponto de Referência:* ${reference}\n`;
        }
        if (orderObs) {
            msg += `*Observações do Pedido:* ${orderObs}\n`;
        }
        msg += `*Forma de Pagamento:* ${payment}\n`;
        msg += `----------------------------------\n`;
        msg += `*ITENS DO PEDIDO:*\n`;

        cart.forEach(item => {
            let sub = item.price ? `(R$ ${(item.price * item.quantity).toFixed(2).replace('.', ',')})` : '';
            msg += `• ${item.quantity}x *${item.name}* ${item.selectedOption ? `(${item.selectedOption})` : ''} ${sub}\n`;
            if (item.observation) {
                msg += `   _Obs: ${item.observation}_\n`;
            }
        });

        const totalPrice = cart.reduce((acc, c) => acc + (c.price * c.quantity), 0);
        msg += `----------------------------------\n`;
        msg += `*TOTAL DO PEDIDO:* R$ ${totalPrice.toFixed(2).replace('.', ',')}\n`;
        msg += `----------------------------------\n`;
        msg += `Aguardando confirmação! Obrigado!`;

        const phone = "5585986050960";
        const url = `https://api.whatsapp.com/send?phone=${phone}&text=${encodeURIComponent(msg)}`;

        saveOrderToHistory(name, address, totalPrice, cart);

        cart = [];
        saveCart();
        closeModal('cartModal');

        window.open(url, '_blank');
    }

    function saveOrderToHistory(name, address, total, items) {
        let history = JSON.parse(localStorage.getItem('amatos_history') || '[]');
        history.unshift({
            date: new Date().toLocaleDateString('pt-BR') + ' ' + new Date().toLocaleTimeString('pt-BR', {hour: '2-digit', minute:'2-digit'}),
            name: name,
            total: total,
            address: address,
            itemCount: items.reduce((acc, i) => acc + i.quantity, 0)
        });
        localStorage.setItem('amatos_history', JSON.stringify(history.slice(0, 15)));
    }

    function renderHistory() {
        const list = document.getElementById('menuList');
        const history = JSON.parse(localStorage.getItem('amatos_history') || '[]');
        if (history.length === 0) {
            list.innerHTML = `<div class="empty-state">Você ainda não realizou pedidos neste dispositivo.</div>`;
            return;
        }

        list.innerHTML = '<h3 style="font-size: 15px; font-weight: 800; margin-bottom: 8px;">Histórico de Pedidos Recentes</h3>';
        history.forEach(h => {
            const div = document.createElement('div');
            div.className = 'card-item';
            div.innerHTML = `
                <div style="font-size: 12px; color: #888;">📅 ${h.date}</div>
                <div style="font-weight: 700; font-size: 15px;">${h.itemCount} item(ns) - R$ ${h.total.toFixed(2).replace('.', ',')}</div>
                <div style="font-size: 12px; color: #555;">📍 ${h.address}</div>
            `;
            list.appendChild(div);
        });
    }
</script>

</body>
</html>
"""

final_html = html_template.replace("%%LOGO_B64%%", logo_b64).replace("%%ITEMS_JSON%%", items_json)

with open('index.html', 'w', encoding='utf-8') as out:
    out.write(final_html)

print('Successfully generated index.html without template syntax issues!')

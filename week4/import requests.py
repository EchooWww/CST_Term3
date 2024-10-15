import requests
from bs4 import BeautifulSoup
import re


def fetch_doc(url):
    """
    Fetches the content of Google Docs given the URL
    """
    response = requests.get(url)
    if response.status_code == 200:
        return response.text
    else:
        raise ValueError("Failed to retrieve the document")


def parse_table(content):
    """
    Parses the HTML table content from the Google Doc to extract character and position data.
    """
    soup = BeautifulSoup(content, "html.parser")

    rows = soup.find_all("tr")[1:]  # Skip the title row

    grid_data = []

    for row in rows:
        cells = row.find_all("td")
        if len(cells) >= 3:
            try:
                x = int(cells[0].get_text().strip())
                char = cells[1].get_text().strip()
                y = int(cells[2].get_text().strip())
                grid_data.append((char, x, y))
            except ValueError:
                # If conversion to int fails, skip the row (bad data)
                continue

    return grid_data


def print_message(data):
    """
    Builds a 2D grid with characters in their correct coordinates
    """
    if not data:
        print("")
    width = max(x for _, x, _ in data) + 1
    height = max(y for _, _, y in data) + 1
    canva = [[" " for _ in range(width)] for _ in range(height)]

    for char, x, y in data:
        data[width - y - 1][x] = char

    for row in data:
        print("".join(row))


def display_message(url):
    doc = fetch_doc(url)
    data = parse_table(doc)
    print_message(data)


url = "https://docs.google.com/document/d/e/2PACX-1vSHesOf9hv2sPOntssYrEdubmMQm8lwjfwv6NPjjmIRYs_FOYXtqrYgjh85jBUebK9swPXh_a5TJ5Kl/pub"
display_message(url)
